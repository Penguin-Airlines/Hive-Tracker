package com.penguinairlines.hivetraker.data.providers.test

import android.location.Location
import com.penguinairlines.hivetraker.data.models.Hive
import com.penguinairlines.hivetraker.data.models.HiveStatus
import com.penguinairlines.hivetraker.data.models.Log
import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.providers.HiveProvider

object TestHiveProvider: HiveProvider {

    var yard = Yard(
        "Test Yard",
        User(
            "Burt Miller",
            "burtmiller@burtsbees.com"
        )
    )
    var location = Location("")
    private var hives = mutableListOf(
        Hive(
            "Hive 1",
            yard,
            location = location,
            status = HiveStatus.CRITICAL,
            frameCount = 5u,
            description = "A beautiful hive with LOGS!",
            logList =  mutableListOf(Log("log1","Hive 1"))
        ),
        Hive(
            "Hive 2",
            yard,
            location = location,
            status = HiveStatus.IN_PROGRESS,
            frameCount = 7u,
            description = "A beautifuler hive",
        ),
        Hive(
            "Hive 3",
            yard,
            location = location,
            status = HiveStatus.OKAY,
            frameCount = 2u,
            description = "The hive I leave alone",

        ),
    )

    private fun copyHive(hive: Hive): Hive {
        return hive.copy(
            yard = yard,
            logList = hive.logList.map { log -> log.copy() }.toMutableList()
        )
    }

    override fun getHive(name: String): Hive {
        hives.forEach { hive ->
            if (hive.name == name ) return copyHive(hive)
        }
        throw NoSuchElementException()
    }

    override fun getHives(): List<Hive> {
        return hives.map { hive -> copyHive(hive) }
    }

    override fun setHive(hive: Hive) {
        hives.remove(hive)
        hives.add(hive)
    }

    override fun setHives(hives: List<Hive>) {
        hives.forEach { hive -> setHive(hive) }
    }
}