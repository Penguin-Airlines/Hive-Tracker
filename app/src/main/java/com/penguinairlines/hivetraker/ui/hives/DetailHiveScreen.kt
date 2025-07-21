package com.penguinairlines.hivetraker.ui.hives

import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.penguinairlines.hivetraker.data.models.Hive
import com.penguinairlines.hivetraker.data.models.HiveStatus
import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import com.penguinairlines.hivetraker.data.models.Log
import com.penguinairlines.hivetraker.ui.hives.logs.LogListItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiveDetailScreen(
    logOnClick: (log: Log) -> Unit,
    hiveData: Hive,
    onEditClick: (hive: Hive) -> Unit = { /* Default edit action */ },
    onBackClick : () -> Unit = { /* Default back navigation handler */ },
    modifier: Modifier = Modifier
) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = hiveData.name
                    )
                },
                modifier = Modifier,
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onEditClick(hiveData)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Edit Hive"
                        )
                    }
                }
            )
        },
    ){ it ->
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        item {
            // Hive name
            Text(
                text = hiveData.name,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Hive status chip
            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Text(
                    text = hiveData.status.text,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        item {
            // Description section
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            )
            Text(
                text = hiveData.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            HorizontalDivider()
        }

        item {
            // Location
            Text(
                text = "Location",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            )
            Text(
                text = hiveData.location.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        }

        item {
            // Frame Count
            Text(
                text = "Frame Count",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = hiveData.frameCount.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        }

        item {
            // Yard
            Text(
                text = "Yard",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = hiveData.yard.name, // assuming Yard has a 'name' property
                style = MaterialTheme.typography.bodyMedium
            )
        }
        item{
            Text(
            style = MaterialTheme.typography.displayMedium,
            text = "Hives"
        )
            HorizontalDivider()
            LazyColumn(
                modifier = Modifier.statusBarsPadding()
            ) {
                items(hiveData.logList) { log ->
                    LogListItem(log,
                        onclick = {
                            logOnClick(log)
                        }
                    )
                }
            }
        }

    }}

}
@Preview(showBackground = true)
@Composable
fun PreviewHiveTemplate() {

    val mockYard = Yard(
        "Yard 1",
        User(
            "Burt Miller",
            "burtmiller@burtsbees.com"
        )
    )
    val location = Location("")

    val mockHive = Hive(
        name = "Hive 1",
        yard = mockYard,
        location = location,
        status = HiveStatus.CRITICAL,
        frameCount = 5u,
        description = "A beautiful hive with very active bees."
    )
    mockHive.addLog(Log("log1",mockHive.name))

}