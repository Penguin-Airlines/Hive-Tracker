package com.penguinairlines.hivetraker.ui.recordings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.providers.RecordingProvider
import com.penguinairlines.hivetraker.data.providers.test.TestProvider
import kotlinx.serialization.Serializable

@Composable
fun RecordingNavHost(
    providerFactory: TestProvider,
    currentYard: Yard,
    ){
    val recordingNavController = rememberNavController()

    val recProvider = providerFactory.ge
}