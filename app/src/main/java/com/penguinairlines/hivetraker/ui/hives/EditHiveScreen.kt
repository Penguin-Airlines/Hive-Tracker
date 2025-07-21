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
import androidx.compose.ui.tooling.preview.Preview
import com.penguinairlines.hivetraker.data.models.HiveStatus

@Composable
fun EditHiveScreen(
    hive: Hive,
    saveHive: (Hive) -> Unit = { /* Default save action */ },
) {
    Text("Editing Hive: " + hive.name,
        modifier = Modifier.fillMaxSize().statusBarsPadding().padding(8.dp))
    


}

@Preview(showBackground = true)
@Composable
fun PreviewEditHiveScreen() {

    val FakeUser = User("Burt Miller","burtmiller@burtsbees.com")
    // Name: Burt Miller
    // Email: burtmiller@burtsbees.com

    val FakeYard = Yard("Yard_1",FakeUser)
    // Yard Name: Yard_1
    // Associated User: Burt Miller

    val FakeLocation = Location("Troy")
    // Location Name: Troy

    val FakeHive = Hive("Hive 1",FakeYard,FakeLocation,HiveStatus.CRITICAL,5u,"A beautiful hive with very active bees.")
    // Hive Name: Hive 1
    // Yard: Yard_1
    // Location: Troy
    // Status: CRITICAL
    // Frame Count: 5
    // Description: A beautiful hive with very active bees.

    //HiveTemplate(hiveData = FakeHive) // Save to hive here

}