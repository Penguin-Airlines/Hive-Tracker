package com.penguinairlines.hivetraker.ui.hives

import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.penguinairlines.hivetraker.data.models.Hive
import com.penguinairlines.hivetraker.data.models.HiveStatus
import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard



@Composable
fun AddHiveScreen(
    onBackClick: () -> Unit,
    onSaveClick: (hiveData: Hive) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var yardName by remember { mutableStateOf("") }
    var frameCountText by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val isFormComplete = name.isNotBlank() &&
            yardName.isNotBlank() &&
            frameCountText.toUIntOrNull() != null &&
            description.isNotBlank()

    Scaffold { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Hive Name") },
                placeholder = { Text("Enter hive name") },
                modifier = Modifier.padding(16.dp)
            )

            OutlinedTextField(
                value = yardName,
                onValueChange = { yardName = it },
                label = { Text("Yard Name") },
                placeholder = { Text("Enter yard name") },
                modifier = Modifier.padding(16.dp)
            )

            OutlinedTextField(
                value = frameCountText,
                onValueChange = { frameCountText = it },
                label = { Text("Frame Count") },
                placeholder = { Text("e.g., 10") },
                modifier = Modifier.padding(16.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                placeholder = { Text("Enter description") },
                modifier = Modifier.padding(16.dp)
            )

            Button(
                onClick = {
                    val fakeUser = User("Burt Miller", "burtmiller@burtsbees.com")
                    val fakeYard = Yard(name = yardName, owner = fakeUser)

                    val fakeLocation = Location("manual").apply {
                        latitude = 0.0
                        longitude = 0.0
                    }

                    val hive = Hive(
                        name = name,
                        yard = fakeYard,
                        location = fakeLocation,
                        status = HiveStatus.OKAY, // or default status
                        frameCount = frameCountText.toUInt(),
                        description = description
                    )

                    onSaveClick(hive)
                },
                enabled = isFormComplete,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Save Hive")
            }
        }
    }
}



@Preview
@Composable
fun PreviewAddHiveScreen() {
    AddHiveScreen(
        onBackClick = { /* No-op */ },
        onSaveClick = { hiveData ->
            // Handle save action here
            println("Hive saved: ${hiveData.name}")
        }
    )
}