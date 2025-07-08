package com.penguinairlines.hivetraker.ui.hives

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.providers.HiveProvider
import com.penguinairlines.hivetraker.data.providers.test.TestProvider

@Composable
fun HivesScreen(modifier: Modifier = Modifier) {
    val currentUser = User("", "")
    val currentYard = Yard("", currentUser)

    val provider = TestProvider()
    val hiveProvider: HiveProvider = provider.getHiveProvider(currentYard, "")
    Column (
        modifier = modifier.fillMaxSize().statusBarsPadding().padding(8.dp),
    ) {

        Text(
            style = MaterialTheme.typography.displayMedium,
            text = "Hives"
        )
        HorizontalDivider()
        LazyColumn(
            modifier = Modifier.fillMaxSize().statusBarsPadding()
        ) {
            items(hiveProvider.getHives()) { hive ->
                Card (
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
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
            }
        }
    }


}


