package com.penguinairlines.hivetraker.ui.hives

import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.penguinairlines.hivetraker.data.models.Hive
import com.penguinairlines.hivetraker.data.models.HiveStatus
import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getColor


@Composable
fun HiveTemplate(
    modifier: Modifier = Modifier,
    hiveData: Hive
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        item {
            // Hive name
            Text(
                text = hiveData.name,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Hive status chip
            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    Color(getColor(LocalContext.current, hiveData.status.color))
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Text(
                    text = hiveData.status.text,
                    color = Color(getColor(LocalContext.current, android.R.color.white)),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        item {
            // Description section
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            )
            Text(
                text = hiveData.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            HorizontalDivider()
        }

        item {
            // Location
            Text(
                text = "Location",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            )
            Text(
                text = hiveData.location.toString() ?: "Unknown",
                style = MaterialTheme.typography.bodyMedium
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        }

        item {
            // Frame Count
            Text(
                text = "Frame Count",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = hiveData.frameCount.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        }

        item {
            // Yard
            Text(
                text = "Yard",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = hiveData.yard.name, // assuming Yard has a 'name' property
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewHiveTemplate() {

    val mockYard = Yard(
        "Yard 1",
        User(
            "Burt Miller",
            "burtmiller@burtsbees.com"
        )
    )
    val location = Location("")

    val mockHive = Hive(
        name = "Hive 1",
        yard = mockYard,
        location = location,
        status = HiveStatus.CRITICAL,
        frameCount = 5u,
        description = "A beautiful hive with very active bees.",
    )

    HiveTemplate(hiveData = mockHive)
}