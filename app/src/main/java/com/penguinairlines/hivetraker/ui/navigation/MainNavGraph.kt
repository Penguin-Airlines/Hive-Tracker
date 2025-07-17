package com.penguinairlines.hivetraker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.penguinairlines.hivetraker.HomeScreen
import com.penguinairlines.hivetraker.RecordingsScreen
import com.penguinairlines.hivetraker.TasksScreen
import com.penguinairlines.hivetraker.ui.hives.HivesNavHost

@Composable
fun MainNavGraph(
    navController: NavHostController,
    startDestination: NavDestination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        NavDestination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    NavDestination.Home -> HomeScreen()
                    NavDestination.Hives -> HivesNavHost()
                    NavDestination.Tasks -> TasksScreen()
                    NavDestination.Recordings -> RecordingsScreen()
                }
            }
        }
    }
}
