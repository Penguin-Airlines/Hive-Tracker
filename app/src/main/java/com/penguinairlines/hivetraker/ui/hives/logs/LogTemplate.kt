package com.penguinairlines.hivetraker.ui.hives.logs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

@Composable
fun LogTemplate(log: Log) {
    val scrollState = rememberScrollState()
    val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Log Details", style = MaterialTheme.typography.headlineMedium)

        LogEntry("Log Name", log.logName)
        LogEntry("Hive Name", log.hiveName)
        LogEntry("Date", dateFormatter.format(log.date))
        LogEntry("Notes", log.notes)
        LogEntry("Temper", log.temper)
        LogEntry("Hive Condition", log.hiveCondition)
        LogEntry("Number of Frames", log.numFrames?.toString())
        LogEntry("Bee Frames Present", log.beeFramesBool?.toYesNo())
        LogEntry("Bee Frame Count", log.numBeeFrames?.toString())
        LogEntry("Honey Frames in Body", log.honeyFramesBoolBody?.toYesNo())
        LogEntry("Honey Frame Count (Body)", log.numHoneyFramesBody?.toString())
        LogEntry("Honey Frames in Top", log.honeyFramesBoolTop?.toYesNo())
        LogEntry("Honey Frame Count (Top)", log.numHoneyFramesTop?.toString())
        LogEntry("Brood (Eggs)", log.broodEgg?.toYesNo())
        LogEntry("Brood (Larvae)", log.broodLarvae?.toYesNo())
        LogEntry("Capped Brood", log.cappedBrood?.toYesNo())
        LogEntry("Laying Pattern", log.layingPatter)
        LogEntry("Queen Seen", log.queenSeen?.toYesNo())
        LogEntry("Queen Notes", log.queenNotes)
        LogEntry("Queen Cells", log.queenCells?.toYesNo())
        LogEntry("Queen Cells Notes", log.queenCellsNotes)
        LogEntry("No Queen", log.noQueen?.toYesNo())
        LogEntry("Drone Cells", log.droneCells?.toYesNo())
        LogEntry("Drones Present", log.dronesBool?.toYesNo())
        LogEntry("Drones Notes", log.dronesNotes)
        LogEntry("Disease", log.disease)
        LogEntry("Pest", log.pest)
        LogEntry("Disease & Pest Notes", log.diseasePestNotes)
        LogEntry("Weather", log.weather)
    }
}
