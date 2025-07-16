package com.penguinairlines.hivetraker.ui.hives

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.penguinairlines.hivetraker.data.models.Hive

@Composable
fun EditHiveScreen(hive: Hive) {
    Text("Editing Hive: " + hive.name,
        modifier = Modifier.fillMaxSize().statusBarsPadding().padding(8.dp))

}