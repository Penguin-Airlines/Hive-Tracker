package com.penguinairlines.hivetraker.ui.hives

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.penguinairlines.hivetraker.data.models.Hive
import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.providers.HiveProvider
import com.penguinairlines.hivetraker.ui.navigation.MainNavGraph
import com.penguinairlines.hivetraker.ui.navigation.NavDestination

@Composable
fun ListHivesScreen(hiveOnClick: (hive: Hive) -> Unit, addHiveOnClick: () -> Unit, hiveProvider: HiveProvider, currentUser: User, currentYard: Yard, modifier: Modifier = Modifier) {
    Scaffold (
        topBar = {
            // TopAppBar can be added here if needed
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
        Column (
            modifier = modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(contentPadding)
                .padding(8.dp)
        ) {

            Text(
                style = MaterialTheme.typography.displayMedium,
                text = "Hives"
            )
            HorizontalDivider()
            LazyColumn(
                modifier = Modifier.statusBarsPadding()
            ) {
                items(hiveProvider.getHives()) { hive ->
                    HiveListItem(hive,
                        onclick = {
                            hiveOnClick(hive)
                        }
                    )
                }
            }
        }
    }
}



