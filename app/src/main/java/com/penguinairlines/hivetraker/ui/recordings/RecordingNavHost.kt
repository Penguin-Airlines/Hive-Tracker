package com.penguinairlines.hivetraker.ui.recordings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.penguinairlines.hivetraker.data.models.Recording
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.providers.test.TestProvider

import kotlinx.serialization.Serializable

@Composable
fun RecordingNavHost(
    providerFactory: TestProvider,
    currentYard: Yard,
    ){
    val recordingNavController = rememberNavController()

    val recProvider = remember { providerFactory.getRecordingProvider(currentYard) }
    NavHost(recordingNavController, startDestination = RecordingsDestination.List){
        composable<RecordingsDestination.List> {
            ListRecordingsScreen(
                onRecordingClick = { recording: Recording ->
                    recordingNavController.navigate(
                        RecordingsDestination.Details(recording.title)
                    )
                },
                addRecordingOnClick = {
                    recordingNavController.navigate(
                        RecordingsDestination.Add
                    )
                },
                recordingProvider = recProvider
            )

        }
        composable<RecordingsDestination.Add> {
            AddRecordingScreen(
                onSave = { newRecording ->
                    recProvider.addRecording(newRecording)
                    recordingNavController.navigateUp() // go back to list
                },
                onBack = {
                    recordingNavController.navigateUp()
                }
            )
        }

        // Details Screen
        composable<RecordingsDestination.Details> { backStackEntry ->
            val args = backStackEntry.toRoute<RecordingsDestination.Details>()
            val recording = remember(args.recName) {
                recProvider.getRecording(args.recName)
            }

            RecordingDetailScreen(
                recording = recording,
                onBack = { recordingNavController.navigateUp() }
            )
        }
    }
}
sealed class RecordingsDestination {
    @Serializable
    object List: RecordingsDestination()
    @Serializable
    data class Details(
        val recName: String
    ): RecordingsDestination()
    @Serializable
    object Add: RecordingsDestination()

}