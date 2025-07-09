package com.penguinairlines.hivetraker.ui.hives

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hive
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getColor
import com.penguinairlines.hivetraker.data.models.Hive


@Composable
private fun HiveImageListItem(hive: Hive, modifier: Modifier = Modifier) {
    Icon(
        Icons.Default.Hive,
        hive.description,
        modifier = modifier
    )
}

@Composable
private fun HiveTextListItem(hive: Hive, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = hive.name
        )
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = "\t" + hive.description
        )
    }
}

@Composable
private fun HiveStatusListItem(hive: Hive, modifier: Modifier = Modifier) {
    Box (
        modifier = modifier
            .background(
                color = Color(getColor(LocalContext.current, hive.status.color)),
                shape = RoundedCornerShape(50)
            )
            .padding(6.dp)
    ) {
        Text(
            text = hive.status.text,
            color = Color(getColor(LocalContext.current, android.R.color.white)),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun HiveListItem(hive: Hive) {
    // Container for the List Item
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left-Icon
            HiveImageListItem(hive)
            // Center Text
            HiveTextListItem(hive,
                Modifier
                    .weight(1f)
                    .padding(horizontal = 6.dp)
            )
            // End Status
            HiveStatusListItem(hive)
        }
    }
}