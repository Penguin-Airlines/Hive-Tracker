package com.penguinairlines.hivetraker.ui.hives
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.penguinairlines.hivetraker.data.models.Hive
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.models.User
import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.penguinairlines.hivetraker.data.models.HiveStatus


@Composable
fun EditHiveScreen(
    hive: Hive,
    saveHive: (Hive) -> Unit = { /* Default save action */ },
) {
    var name by remember { mutableStateOf(hive.name) }
    var yardName by remember { mutableStateOf(hive.yard.name) }
    var frameCountText by remember { mutableStateOf(hive.frameCount.toString()) }
    var description by remember { mutableStateOf(hive.description) }

    val isFormComplete = name.isNotBlank() &&
            yardName.isNotBlank() &&
            frameCountText.toUIntOrNull() != null &&
            description.isNotBlank()

    val fakeUser = hive.yard.owner
    val fakeLocation = hive.location
    val fakeStatus = hive.status

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Text(text = "Editing Hive: ${hive.name}", modifier = Modifier.padding(bottom = 16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Hive Name") },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = yardName,
            onValueChange = { yardName = it },
            label = { Text("Yard Name") },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = frameCountText,
            onValueChange = { frameCountText = it },
            label = { Text("Frame Count") },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Button(
            onClick = {
                val updatedHive = Hive(
                    name = name,
                    yard = Yard(yardName, fakeUser),
                    location = fakeLocation,
                    status = fakeStatus,
                    frameCount = frameCountText.toUInt(),
                    description = description
                )
                saveHive(updatedHive)
            },
            enabled = isFormComplete,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Save Changes")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewEditHiveScreen() {
    val fakeUser = User("Burt Miller", "burtmiller@burtsbees.com")
    val fakeYard = Yard("Yard_1", fakeUser)
    val fakeLocation = Location("Troy")
    val fakeHive = Hive(
        name = "Hive 1",
        yard = fakeYard,
        location = fakeLocation,
        status = HiveStatus.CRITICAL,
        frameCount = 5u,
        description = "A beautiful hive with very active bees."
    )

    EditHiveScreen(
        hive = fakeHive,
        saveHive = { println("Hive saved: ${it.name}") }
    )
}
