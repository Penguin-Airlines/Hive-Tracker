package com.penguinairlines.hivetraker.ui.navigation

import androidx.compose.material.icons.filled.Hive
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.outlined.Hive
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.KeyboardVoice
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.penguinairlines.hivetraker.R

enum class NavDestination(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconSelected: androidx.compose.ui.graphics.vector.ImageVector,
    val contentDescription: Int
) {
    Home(
        route = "home",
        label = "Home",
        icon = androidx.compose.material.icons.Icons.Outlined.Home,
        iconSelected = androidx.compose.material.icons.Icons.Filled.Home,
        contentDescription = R.string.home_page_content_description
    ),
    Hives(
        route = "hives",
        label = "Hives",
        icon = androidx.compose.material.icons.Icons.Outlined.Hive,
        iconSelected = androidx.compose.material.icons.Icons.Filled.Hive,
        contentDescription = R.string.hives_page_content_description
    ),
    Tasks(
        route = "tasks",
        label = "Tasks",
        icon = androidx.compose.material.icons.Icons.Outlined.Task,
        iconSelected = androidx.compose.material.icons.Icons.Filled.Task,
        contentDescription = R.string.tasks_page_content_description
    ),
    Recordings(
        route = "recordings",
        label = "Recordings",
        icon = androidx.compose.material.icons.Icons.Outlined.KeyboardVoice,
        iconSelected = androidx.compose.material.icons.Icons.Filled.KeyboardVoice,
        contentDescription = R.string.recordings_page_content_description
    );
}

@Composable
fun MainBottomBar(
    navController: NavHostController,
    onDestinationSelected: (Int) -> Unit
) {
    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavDestination.entries.forEach { destination ->
            val selected: Boolean = currentRoute == destination.route
            val icon = if (selected) destination.iconSelected else destination.icon

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(route = destination.route)
                },
                icon = {
                    Icon (
                        icon,
                        contentDescription = stringResource(destination.contentDescription)
                    )
                },
                label = { Text(destination.label) }
            )
        }
    }
}