package com.penguinairlines.hivetraker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.penguinairlines.hivetraker.R

sealed class NavDestination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val iconSelected: ImageVector,
    val contentDescription: Int
) {
    object Home : NavDestination(
        route = "home",
        label = "Home",
        icon = Icons.Outlined.Home,
        iconSelected = Icons.Filled.Home,
        contentDescription = R.string.home_page_content_description
    )
    object Hives : NavDestination(
        route = "hives",
        label = "Hives",
        icon = Icons.Outlined.Hive,
        iconSelected = Icons.Filled.Hive,
        contentDescription = R.string.home_page_content_description
    )
    object Tasks : NavDestination(
        route = "tasks",
        label = "Tasks",
        icon = Icons.Outlined.Task,
        iconSelected = Icons.Filled.Task,
        contentDescription = R.string.home_page_content_description
    )
    object Recordings : NavDestination(
        route = "recordings",
        label = "Recordings",
        icon = Icons.Outlined.KeyboardVoice,
        iconSelected = Icons.Filled.KeyboardVoice,
        contentDescription = R.string.home_page_content_description
    )

    companion object {
        val entries = listOf(Home, Hives, Tasks, Recordings)
    }
}