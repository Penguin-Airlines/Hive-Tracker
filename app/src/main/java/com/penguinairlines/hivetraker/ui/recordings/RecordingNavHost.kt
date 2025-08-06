package com.penguinairlines.hivetraker.ui.recordings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.penguinairlines.hivetraker.data.providers.RecordingProvider
import kotlinx.serialization.Serializable

@Composable
RecordingNavHost(
    providerFactory: TestProvider,
    currentYard: Yard,
    ){

}