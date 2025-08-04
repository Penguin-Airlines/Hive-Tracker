package com.penguinairlines.hivetraker.ui.hives.logs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.penguinairlines.hivetraker.data.models.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue



@Composable
fun AddLogScreen(
    hiveName: String,
    lastLog: Log? = null,
    onSaveClick: (Log) -> Unit,
    onCancelClick: () -> Unit
) {
    var logName by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var temper by remember { mutableStateOf("") }
    var hiveCondition by remember { mutableStateOf("") }
    var numFramesText by remember { mutableStateOf("") }
    var beeFramesBool by remember { mutableStateOf(false) }
    var numBeeFramesText by remember { mutableStateOf("") }
    var honeyFramesBoolBody by remember { mutableStateOf(false) }
    var numHoneyFramesBodyText by remember { mutableStateOf("") }
    var honeyFramesBoolTop by remember { mutableStateOf(false) }
    var numHoneyFramesTopText by remember { mutableStateOf("") }
    var broodEgg by remember { mutableStateOf(false) }
    var broodLarvae by remember { mutableStateOf(false) }
    var cappedBrood by remember { mutableStateOf(false) }
    var layingPattern by remember { mutableStateOf("") }
    var queenSeen by remember { mutableStateOf(false) }
    var queenNotes by remember { mutableStateOf("") }
    var queenCells by remember { mutableStateOf(false) }
    var queenCellsNotes by remember { mutableStateOf("") }
    var noQueen by remember { mutableStateOf(false) }
    var droneCells by remember { mutableStateOf(false) }
    var dronesBool by remember { mutableStateOf(false) }
    var dronesNotes by remember { mutableStateOf("") }
    var disease by remember { mutableStateOf("") }
    var pest by remember { mutableStateOf("") }
    var diseasePestNotes by remember { mutableStateOf("") }
    var weather by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val tabTitles = listOf("Basic", "Queen", "Drones", "Disease")
    var selectedTab by remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            Column {
                Text(
                    text = "Add Log",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
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
        },
        bottomBar = {
            Row(Modifier.padding(16.dp)) {
                Button(onClick = onCancelClick, modifier = Modifier.weight(1f)) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        val log = Log(
                            logName = logName,
                            hiveName = hiveName,
                            notes = notes.takeIf { it.isNotBlank() },
                            temper = temper.takeIf { it.isNotBlank() },
                            hiveCondition = hiveCondition.takeIf { it.isNotBlank() },
                            beeFramesBool = beeFramesBool,
                            numBeeFrames = numBeeFramesText.toIntOrNull(),
                            layingPatter = layingPattern.takeIf { it.isNotBlank() },
                            queenSeen = queenSeen,
                            queenNotes = queenNotes.takeIf { it.isNotBlank() },
                            queenCells = queenCells,
                            queenCellsNotes = queenCellsNotes.takeIf { it.isNotBlank() },
                            noQueen = noQueen,
                            droneCells = droneCells,
                            dronesBool = dronesBool,
                            dronesNotes = dronesNotes.takeIf { it.isNotBlank() },
                            disease = disease.takeIf { it.isNotBlank() },
                            pest = pest.takeIf { it.isNotBlank() },
                            diseasePestNotes = diseasePestNotes.takeIf { it.isNotBlank() },
                            weather = null, // Fill in other fields as needed
                        )
                        onSaveClick(log)
                    },
                    modifier = Modifier.weight(1f),
                    enabled = logName.isNotBlank()
                ) {
                    Text("Save")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            when (selectedTab) {
                0 -> {
                    OutlinedTextField(value = logName, onValueChange = { logName = it }, label = { Text("Log Name*") })
                    OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Notes") })
                    OutlinedTextField(value = temper, onValueChange = { temper = it }, label = { Text("Temper") })
                    OutlinedTextField(value = hiveCondition, onValueChange = { hiveCondition = it }, label = { Text("Hive Condition") })
                    OutlinedTextField(value = numBeeFramesText, onValueChange = { numBeeFramesText = it }, label = { Text("Bee Frames") })
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = beeFramesBool, onCheckedChange = { beeFramesBool = it })
                        Text("Bee Frames Present")
                    }
                }
                1 -> {
                    OutlinedTextField(value = layingPattern, onValueChange = { layingPattern = it }, label = { Text("Laying Pattern") })
                    OutlinedTextField(value = queenNotes, onValueChange = { queenNotes = it }, label = { Text("Queen Notes") })
                    OutlinedTextField(value = queenCellsNotes, onValueChange = { queenCellsNotes = it }, label = { Text("Queen Cells Notes") })
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = queenSeen, onCheckedChange = { queenSeen = it })
                        Text("Queen Seen")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = queenCells, onCheckedChange = { queenCells = it })
                        Text("Queen Cells Present")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = noQueen, onCheckedChange = { noQueen = it })
                        Text("No Queen")
                    }
                }
                2 -> {
                    OutlinedTextField(value = dronesNotes, onValueChange = { dronesNotes = it }, label = { Text("Drone Notes") })
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = droneCells, onCheckedChange = { droneCells = it })
                        Text("Drone Cells")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = dronesBool, onCheckedChange = { dronesBool = it })
                        Text("Drones Present")
                    }
                }
                3 -> {
                    OutlinedTextField(value = disease, onValueChange = { disease = it }, label = { Text("Disease") })
                    OutlinedTextField(value = pest, onValueChange = { pest = it }, label = { Text("Pest") })
                    OutlinedTextField(value = diseasePestNotes, onValueChange = { diseasePestNotes = it }, label = { Text("Disease/Pest Notes") })
                }
            }
        }
    }

}
