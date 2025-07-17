package com.penguinairlines.hivetraker.ui.hives

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
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
import com.penguinairlines.hivetraker.data.models.Hive

@Composable
fun AddHiveScreen(
    onBackClick: () -> Unit,
    onSaveClick: (hiveData: Hive) -> Unit,
    modifier: Modifier = Modifier
) {
    var hiveName by remember { mutableStateOf("") }
    Scaffold {
        contentPadding ->

        OutlinedTextField(
            value = hiveName,
            onValueChange = { hiveName = it },
            label = {Text("Hive Name")},
            placeholder = {Text("Enter hive name")},
        )
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