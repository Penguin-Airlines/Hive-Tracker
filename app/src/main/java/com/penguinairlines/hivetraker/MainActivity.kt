package com.penguinairlines.hivetraker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.penguinairlines.hivetraker.ui.navigation.MainBottomBar
import com.penguinairlines.hivetraker.ui.navigation.MainNavHost
import com.penguinairlines.hivetraker.ui.navigation.NavDestination
import com.penguinairlines.hivetraker.ui.theme.HiveTrakerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HiveTrakerTheme {
                val navController = rememberNavController()
                val startDestination = NavDestination.Home

                Scaffold(
                    modifier = Modifier,
                    bottomBar = {
                        MainBottomBar(
                            navController = navController,
                            onDestinationSelected = { index ->

                            }

                        )
                    }

                ) { contentPadding ->
                    MainNavHost(navController, startDestination, modifier = Modifier.padding(contentPadding))
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
