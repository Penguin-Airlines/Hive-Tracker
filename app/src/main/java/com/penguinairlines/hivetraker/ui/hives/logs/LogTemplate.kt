package com.penguinairlines.hivetraker.ui.hives.logs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.penguinairlines.hivetraker.data.models.Log
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun LogEntry(label: String, value: String?) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Text(text = value ?: "N/A", style = MaterialTheme.typography.bodyLarge)
    }
}

fun Boolean.toYesNo(): String = if (this) "Yes" else "No"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogTemplate(log: Log, onLogBackClick: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Log Details") },
                navigationIcon = {
                    IconButton(onClick = onLogBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { LogEntry("Log Name", log.logName) }
            item { LogEntry("Hive Name", log.hiveName) }
            item { LogEntry("Date", dateFormatter.format(log.date)) }
            item { LogEntry("Notes", log.notes) }
            item { LogEntry("Temper", log.temper) }
            item { LogEntry("Hive Condition", log.hiveCondition) }
            item { LogEntry("Number of Frames", log.numFrames?.toString()) }
            item { LogEntry("Bee Frames Present", log.beeFramesBool?.toYesNo()) }
            item { LogEntry("Bee Frame Count", log.numBeeFrames?.toString()) }
            item { LogEntry("Honey Frames in Body", log.honeyFramesBoolBody?.toYesNo()) }
            item { LogEntry("Honey Frame Count (Body)", log.numHoneyFramesBody?.toString()) }
            item { LogEntry("Honey Frames in Top", log.honeyFramesBoolTop?.toYesNo()) }
            item { LogEntry("Honey Frame Count (Top)", log.numHoneyFramesTop?.toString()) }
            item { LogEntry("Brood (Eggs)", log.broodEgg?.toYesNo()) }
            item { LogEntry("Brood (Larvae)", log.broodLarvae?.toYesNo()) }
            item { LogEntry("Capped Brood", log.cappedBrood?.toYesNo()) }
            item { LogEntry("Laying Pattern", log.layingPatter) }
            item { LogEntry("Queen Seen", log.queenSeen?.toYesNo()) }
            item { LogEntry("Queen Notes", log.queenNotes) }
            item { LogEntry("Queen Cells", log.queenCells?.toYesNo()) }
            item { LogEntry("Queen Cells Notes", log.queenCellsNotes) }
            item { LogEntry("No Queen", log.noQueen?.toYesNo()) }
            item { LogEntry("Drone Cells", log.droneCells?.toYesNo()) }
            item { LogEntry("Drones Present", log.dronesBool?.toYesNo()) }
            item { LogEntry("Drones Notes", log.dronesNotes) }
            item { LogEntry("Disease", log.disease) }
            item { LogEntry("Pest", log.pest) }
            item { LogEntry("Disease & Pest Notes", log.diseasePestNotes) }
            item { LogEntry("Weather", log.weather) }
        }
    }
}

