package com.penguinairlines.hivetraker.ui

import android.util.Log
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
import com.penguinairlines.hivetraker.ui.navigation.NavDestination

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