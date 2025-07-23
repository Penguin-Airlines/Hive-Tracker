package com.penguinairlines.hivetraker.ui.hives.logs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.penguinairlines.hivetraker.data.models.Log

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

    Scaffold(
        topBar = {
            Text("Add Log", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
        },
        bottomBar = {
            Row(Modifier.padding(16.dp)) {
                Button(onClick = onCancelClick, modifier = Modifier.weight(1f)) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        val newLog = Log(
                            logName = logName,
                            hiveName = hiveName,
                            notes = notes.takeIf { it.isNotBlank() },
                            temper = temper.takeIf { it.isNotBlank() },
                            hiveCondition = hiveCondition.takeIf { it.isNotBlank() },
                            numFrames = numFramesText.toIntOrNull() ?: lastLog?.numFrames,
                            beeFramesBool = beeFramesBool,
                            numBeeFrames = numBeeFramesText.toIntOrNull(),
                            honeyFramesBoolBody = honeyFramesBoolBody,
                            numHoneyFramesBody = numHoneyFramesBodyText.toIntOrNull(),
                            honeyFramesBoolTop = honeyFramesBoolTop,
                            numHoneyFramesTop = numHoneyFramesTopText.toIntOrNull(),
                            broodEgg = broodEgg,
                            broodLarvae = broodLarvae,
                            cappedBrood = cappedBrood,
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
                            weather = weather.takeIf { it.isNotBlank() }
                        )
                        onSaveClick(newLog)
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
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            OutlinedTextField(value = logName, onValueChange = { logName = it }, label = { Text("Log Name*") })
            OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Notes") })
            OutlinedTextField(value = temper, onValueChange = { temper = it }, label = { Text("Temperament") })
            OutlinedTextField(value = hiveCondition, onValueChange = { hiveCondition = it }, label = { Text("Hive Condition") })
            OutlinedTextField(value = numFramesText, onValueChange = { numFramesText = it }, label = { Text("Frame Count") })
            OutlinedTextField(value = numBeeFramesText, onValueChange = { numBeeFramesText = it }, label = { Text("Bee Frames Count") })
            OutlinedTextField(value = numHoneyFramesBodyText, onValueChange = { numHoneyFramesBodyText = it }, label = { Text("Honey Frames (Body)") })
            OutlinedTextField(value = numHoneyFramesTopText, onValueChange = { numHoneyFramesTopText = it }, label = { Text("Honey Frames (Top)") })
            OutlinedTextField(value = layingPattern, onValueChange = { layingPattern = it }, label = { Text("Laying Pattern") })
            OutlinedTextField(value = queenNotes, onValueChange = { queenNotes = it }, label = { Text("Queen Notes") })
            OutlinedTextField(value = queenCellsNotes, onValueChange = { queenCellsNotes = it }, label = { Text("Queen Cell Notes") })
            OutlinedTextField(value = dronesNotes, onValueChange = { dronesNotes = it }, label = { Text("Drone Notes") })
            OutlinedTextField(value = disease, onValueChange = { disease = it }, label = { Text("Disease") })
            OutlinedTextField(value = pest, onValueChange = { pest = it }, label = { Text("Pests") })
            OutlinedTextField(value = diseasePestNotes, onValueChange = { diseasePestNotes = it }, label = { Text("Disease/Pest Notes") })
            OutlinedTextField(value = weather, onValueChange = { weather = it }, label = { Text("Weather") })

            // You can also use Switch/Checkbox/Toggle for boolean fields
            Text("Use switches or checkboxes for booleans in your actual UI")
        }
    }
}
