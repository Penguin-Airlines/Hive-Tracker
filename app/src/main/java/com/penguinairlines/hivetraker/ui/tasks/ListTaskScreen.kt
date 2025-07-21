package com.penguinairlines.hivetraker.ui.tasks

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.penguinairlines.hivetraker.data.models.Task
import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.providers.TaskProvider
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTaskScreen(
    taskProvider: TaskProvider,
    onAddTaskClick: () -> Unit,
) {
    Scaffold (
        topBar = {
            androidx.compose.material3.CenterAlignedTopAppBar(
                title = {
                    Text(text = "All Tasks")
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddTaskClick,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Task",
                    )
                },
                text = {
                    Text(
                        text = "Add Task",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            )
        },
        modifier = Modifier
    ) {
        contentPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            // Use the content padding to avoid overlapping with the top bar
            contentPadding = contentPadding
        ) {
            // Sort tasks by due date and filter out past due tasks
            val now = Calendar.getInstance()
            val tasks = taskProvider.getTasks()
                .filter { it.dueDate.after(now) || isSameDay(it.dueDate, now) }
                .sortedBy { it.dueDate.time }
            // Group tasks by month
            tasks.groupBy { Pair(it.dueDate.get(Calendar.YEAR), it.dueDate.get(Calendar.MONTH)) }.forEach { (monthYear, tasksInMonth) ->
                val (_, month) = monthYear
                // Display the month header
                item {
                    MonthHeader(month)
                }

                // Display day in the month
                tasksInMonth.groupBy { it.dueDate.get(Calendar.DAY_OF_MONTH) }.forEach { (_, tasksOnDay) ->
                    // Display the day header
                    item {
                        Row {
                            // Display the date circle
                            DateCircle(
                                date = tasksOnDay.first().dueDate,
                                modifier = Modifier.padding(8.dp)
                            )

                            // Display the tasks for that day
                            Column {
                                tasksOnDay.forEach { task ->
                                    val context = LocalContext.current
                                    TaskListItem(
                                        task = task,
                                        onClick = {
                                            // Handle task click
                                            Toast.makeText(context, it.name + " was clicked", Toast.LENGTH_SHORT).show()
                                        }
                                    )
                                }
                            }

                        }

                    }
                }
            }

        }
    }
}

@Composable
fun TaskListItem(
    task: Task,
    onClick: (Task) -> Unit
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        onClick = { onClick(task) }
    )
    {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = task.name,
            )
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = task.description,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun DateCircle(date: Calendar, modifier: Modifier = Modifier) {
    // Get today's date for comparison
    val today = Calendar.getInstance()
    val isToday =
        date.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
        date.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)

    Box(
        modifier = modifier
            .size(56.dp)
            .then(
                if (isToday)
                    Modifier.background(MaterialTheme.colorScheme.primary, CircleShape)
                else
                    Modifier // No background if not today
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val weekday = date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) ?: ""
            Text(
                text = weekday,
                style = MaterialTheme.typography.labelMedium,
                color = if (isToday) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = date.get(Calendar.DAY_OF_MONTH).toString(),
                style = MaterialTheme.typography.titleMedium,
                color = if (isToday) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MonthHeader(month: Int) {
    // Get the month name from the Calendar instance
    val monthName = Calendar.getInstance().apply {
        set(Calendar.MONTH, month)
    }.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) ?: ""

    Text(
        text = monthName,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}

/**
 * Returns true if the two calendars represent the same day (year, month, day).
 */
fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

@Preview
@Composable
fun TaskListITemPreview() {
    val testTask = Task(
        name = "Test Task",
        dueDate = Calendar.getInstance(),
        description = "This is a test task description.",
        yard = Yard(
            name = "Test Yard",
            owner = User(
                name = "Test User",
                email = "e"
            )
        )

    )
    TaskListItem(testTask) { }
}
