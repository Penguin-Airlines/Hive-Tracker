package com.penguinairlines.hivetraker.data.providers.test

import com.penguinairlines.hivetraker.data.providers.LogProvider
import com.penguinairlines.hivetraker.data.models.Log
import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard

object TestLogProvider : LogProvider {
    private val logsByHive: MutableMap<String, MutableList<Log>> = mutableMapOf()
    var yard = Yard(
        "Test Yard",
        User(
            "Burt Miller",
            "burtmiller@burtsbees.com"
        )
    )
    override fun addLog(hiveName: String, log: Log) {
        val hiveLogs = logsByHive.getOrPut(hiveName) { mutableListOf() }
        hiveLogs.add(log)
    }

    override fun getLogsForHive(hiveName: String): List<Log> {
        return logsByHive[hiveName] ?: emptyList()
    }

    override fun removeLog(hiveName: String, log: Log) {
        logsByHive[hiveName]?.remove(log)
    }

    override fun clearLogsForHive(hiveName: String) {
        logsByHive.remove(hiveName)
    }

    override fun getAllLogs(): List<Log> {
        return logsByHive.values.flatten()
    }

    override fun getLogsMap(): Map<String, List<Log>> {
        return logsByHive.mapValues { it.value.toList() }
    }
    override fun getLogByHiveAndName(hiveName: String, logName: String): Log?{
        val hiveLogs = logsByHive.getOrPut(hiveName) { mutableListOf() }
        hiveLogs.forEach { log: Log ->
            if (log.logName.equals(logName)){
                return log
            }
        }
        return null
    }

}