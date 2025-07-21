package com.penguinairlines.hivetraker.ui.hives

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.penguinairlines.hivetraker.data.models.Hive
import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.providers.HiveProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListHivesScreen(hiveOnClick: (hive: Hive) -> Unit, addHiveOnClick: () -> Unit, hiveProvider: HiveProvider, currentUser: User, currentYard: Yard, modifier: Modifier = Modifier) {
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "All Hives"
                    )
                },
                modifier = Modifier
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = addHiveOnClick,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Hive"
                    )
                },
                text = {
                    Text(
                        text = "Add Hive",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
            )
        },
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                items(hiveProvider.getHives().sortedBy { it.status.ordinal }) { hive ->
                    HiveListItem(hive,
                        onclick = {
                            hiveOnClick(hive)
                        }
                    )
                }
            }
    }
}



