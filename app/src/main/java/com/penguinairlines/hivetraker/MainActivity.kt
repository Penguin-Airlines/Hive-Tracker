package com.penguinairlines.hivetraker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.penguinairlines.hivetraker.ui.hives.HivesScreen
import com.penguinairlines.hivetraker.ui.theme.HiveTrakerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HiveTrakerTheme {
                val navController = rememberNavController()
                val startDestination = NavDestination.HOME
                var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

                Scaffold(
                    modifier = Modifier,
                    bottomBar = {
                        NavigationBar(
                            windowInsets = NavigationBarDefaults.windowInsets,
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ) {
                            NavDestination.entries.forEachIndexed { index, destination ->
                                val selected: Boolean = selectedDestination == index
                                val icon = if (selected) destination.iconSelected else destination.icon
                                NavigationBarItem(
                                    selected = selected,
                                    onClick = {
                                        navController.navigate(route = destination.route)
                                        selectedDestination = index
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
                ) { contentPadding ->
                    HiveTrackerNavHost(navController, startDestination, modifier = Modifier.padding(contentPadding))
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Home")
    }
}

@Composable
fun TasksScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Tasks")
    }
}

@Composable
fun RecordingsScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Recordings")
    }
}

enum class NavDestination (
    val route: String,
    val label: String,
    val icon: ImageVector,
    val iconSelected: ImageVector,
    val contentDescription: Int
) {
    HOME("home", "Home", Icons.Outlined.Home, Icons.Filled.Home, R.string.home_page_content_description),
    HIVES("hives", "Hives", Icons.Outlined.Hive, Icons.Filled.Hive, R.string.home_page_content_description),
    TASKS("tasks", "Tasks", Icons.Outlined.Task, Icons.Filled.Task, R.string.home_page_content_description),
    RECORDINGS("recordings", "Recordings", Icons.Outlined.KeyboardVoice, Icons.Filled.KeyboardVoice, R.string.home_page_content_description)
}

@Composable
fun HiveTrackerNavHost(
    navController: NavHostController,
    startDestination: NavDestination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        NavDestination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    NavDestination.HOME -> HomeScreen()
                    NavDestination.HIVES -> HivesScreen()
                    NavDestination.TASKS -> TasksScreen()
                    NavDestination.RECORDINGS -> RecordingsScreen()
                }
            }
        }
    }
}