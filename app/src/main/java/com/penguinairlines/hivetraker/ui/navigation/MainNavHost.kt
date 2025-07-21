package com.penguinairlines.hivetraker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.penguinairlines.hivetraker.HomeScreen
import com.penguinairlines.hivetraker.RecordingsScreen
import com.penguinairlines.hivetraker.ui.hives.HivesNavHost
import com.penguinairlines.hivetraker.ui.tasks.TasksNavHost
import kotlinx.serialization.Serializable

@Composable
fun MainNavHost(
    navController: NavHostController,
    startDestination: NavDestination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainDestination.Home,
        modifier = modifier
    ) {
        composable<MainDestination.Home> {
            HomeScreen()
        }
        composable<MainDestination.Hives> {
            HivesNavHost()
        }
        composable<MainDestination.Tasks> {
            TasksNavHost()
        }
        composable< MainDestination.Recordings> {
            RecordingsScreen()
        }
    }
}

sealed class MainDestination() {
    @Serializable
    object Home : MainDestination()

    @Serializable
    object Hives : MainDestination()

    @Serializable
    object Tasks : MainDestination()

    @Serializable
    object Recordings : MainDestination()
}