package com.penguinairlines.hivetraker.data.providers.test

import android.location.Location
import com.penguinairlines.hivetraker.data.providers.HiveProvider
import com.penguinairlines.hivetraker.data.models.Hive
import com.penguinairlines.hivetraker.data.models.HiveStatus
import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.models.Log

class TestHiveProvider: HiveProvider {
    private var yard = Yard(
        "Yard 1",
        User(
            "Burt Miller",
            "burtmiller@burtsbees.com"
        )
    )
    private var location = Location("")
    private var hives = mutableListOf<Hive>(
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
    override fun getHive(name: String): Hive {
        hives.forEach { hive ->
            if (hive.name == name ) return hive.copy()
        }
        throw NoSuchElementException()
    }

    override fun getHives(): List<Hive> {
        return hives.map { hive -> hive.copy() }
    }

    override fun setHive(hive: Hive) {
        hives.remove(hive)
        hives.add(hive)
    }

    override fun setHives(hives: List<Hive>) {
        hives.forEach { hive -> setHive(hive) }
    }

    fun setLog(hive: Hive, log:Log){
        hives.forEach{ i ->
            if (i.name == hive.name ) i.addLog(log)
        }
    }
}