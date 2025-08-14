package com.penguinairlines.hivetraker.ui.recordings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.penguinairlines.hivetraker.data.models.Recording
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.providers.RecordingProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListRecordingsScreen(
    onRecordingClick: (recording : Recording) -> Unit,
    addRecordingOnClick: () -> Unit,
    modifier: Modifier = Modifier,
    recordingProvider: RecordingProvider
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "All Recordings")
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = addRecordingOnClick,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Recording"
                    )
                },
                text = {
                    Text(
                        text = "Add Recording",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
            )
        },
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            items(
                recordingProvider.getRecordings().sortedBy { it.date } // Or another field, depending on desired order
            ) { recording ->
                RecordingListItem(
                    recording,
                    onClick = { onRecordingClick(recording) }
                )
            }
        }
    }
}