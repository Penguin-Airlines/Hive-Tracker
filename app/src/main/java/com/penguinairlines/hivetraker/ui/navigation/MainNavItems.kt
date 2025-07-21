package com.penguinairlines.hivetraker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hive
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.outlined.Hive
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.KeyboardVoice
import androidx.compose.material.icons.outlined.Task
import androidx.compose.ui.graphics.vector.ImageVector
import com.penguinairlines.hivetraker.R

sealed class MainNavItems(
    val route: MainDestination,
    val label: String,
    val icon: ImageVector,
    val iconSelected: ImageVector,
    val contentDescription: Int
) {
    data object Home : MainNavItems(
        route = MainDestination.Home,
        label = "Home",
        icon = Icons.Outlined.Home,
        iconSelected = Icons.Filled.Home,
        contentDescription = R.string.home_page_content_description
    )

    data object Hives : MainNavItems(
        route = MainDestination.Hives,
        label = "Hives",
        icon = Icons.Outlined.Hive,
        iconSelected = Icons.Filled.Hive,
        contentDescription = R.string.hives_page_content_description
    )

    data object Tasks : MainNavItems(
        route = MainDestination.Tasks,
        label = "Tasks",
        icon = Icons.Outlined.Task,
        iconSelected = Icons.Filled.Task,
        contentDescription = R.string.tasks_page_content_description
    )

    data object Recordings : MainNavItems(
        route = MainDestination.Recordings,
        label = "Recordings",
        icon = Icons.Outlined.KeyboardVoice,
        iconSelected = Icons.Filled.KeyboardVoice,
        contentDescription = R.string.recordings_page_content_description
    )

    companion object {
        fun values(): Array<MainNavItems> {
            return arrayOf(Home, Hives, Tasks, Recordings)
        }
    }
}