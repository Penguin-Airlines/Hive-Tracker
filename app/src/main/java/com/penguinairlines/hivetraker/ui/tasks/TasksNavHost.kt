package com.penguinairlines.hivetraker.ui.tasks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.providers.test.TestProvider
import kotlinx.serialization.Serializable

@Composable
fun TasksNavHost(providerFactory: TestProvider, currentYard: Yard) {
    val taskNavController = rememberNavController()

    val currentTaskProvider = remember { providerFactory.getTaskProvider(currentYard) }

    NavHost(
        taskNavController, startDestination = TasksDestination.List
    ) {
        composable<TasksDestination.List> {
            ListTaskScreen(
                taskProvider = currentTaskProvider,
                onAddTaskClick = {
                    taskNavController.navigate(TasksDestination.Add)
                },
            )
        }
        composable<TasksDestination.Add> {
            AddTaskScreen(
                onBack = { taskNavController.navigateUp() },
                onSave = { task ->
                    currentTaskProvider.setTask(task)
                    taskNavController.navigateUp()
                },
                currentYard = currentYard,
                modifier = Modifier
            )
        }
    }
}

sealed class TasksDestination {
    @Serializable
    object List : TasksDestination()
    @Serializable
    object Add : TasksDestination()
}
