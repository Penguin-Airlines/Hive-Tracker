package com.penguinairlines.hivetraker.ui.hives.logs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.penguinairlines.hivetraker.data.models.Log
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

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
fun LogTemplate(
    log: Log,
    onLogBackClick: () -> Unit = {}
) {
    val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val tabTitles = listOf("Basic", "Queen", "Drones", "Disease")
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            Column {
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
                TabRow(selectedTabIndex = selectedTab) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (selectedTab) {
                0 -> { // Basic
                    item { LogEntry("Log Name", log.logName) }
                    item { LogEntry("Hive Name", log.hiveName) }
                    item { LogEntry("Date", dateFormatter.format(log.date)) }
                    item { LogEntry("Notes", log.notes) }
                    item { LogEntry("Temper", log.temper) }
                    item { LogEntry("Hive Condition", log.hiveCondition) }
                    item { LogEntry("Bee Frames Present", log.beeFramesBool?.toYesNo()) }
                    item { LogEntry("Bee Frame Count", log.numBeeFrames?.toString()) }
                    item { LogEntry("Honey Frames in Body", log.honeyFramesBoolBody?.toYesNo()) }
                    item { LogEntry("Honey Frame Count (Body)", log.numHoneyFramesBody?.toString()) }
                    item { LogEntry("Honey Frames in Top", log.honeyFramesBoolTop?.toYesNo()) }
                    item { LogEntry("Honey Frame Count (Top)", log.numHoneyFramesTop?.toString()) }
                }
                1 -> { // Queen
                    item { LogEntry("Brood (Eggs)", log.broodEgg?.toYesNo()) }
                    item { LogEntry("Brood (Larvae)", log.broodLarvae?.toYesNo()) }
                    item { LogEntry("Capped Brood", log.cappedBrood?.toYesNo()) }
                    item { LogEntry("Laying Pattern", log.layingPatter) }
                    item { LogEntry("Queen Seen", log.queenSeen?.toYesNo()) }
                    item { LogEntry("Queen Notes", log.queenNotes) }
                    item { LogEntry("Queen Cells", log.queenCells?.toYesNo()) }
                    item { LogEntry("Queen Cells Notes", log.queenCellsNotes) }
                    item { LogEntry("No Queen", log.noQueen?.toYesNo()) }
                }
                2 -> { // Drones
                    item { LogEntry("Drone Cells", log.droneCells?.toYesNo()) }
                    item { LogEntry("Drones Present", log.dronesBool?.toYesNo()) }
                    item { LogEntry("Drones Notes", log.dronesNotes) }
                }
                3 -> { // Disease
                    item { LogEntry("Disease", log.disease) }
                    item { LogEntry("Pest", log.pest) }
                    item { LogEntry("Disease & Pest Notes", log.diseasePestNotes) }
                    item { LogEntry("Weather", log.weather) }
                }
            }
        }
    }
}


