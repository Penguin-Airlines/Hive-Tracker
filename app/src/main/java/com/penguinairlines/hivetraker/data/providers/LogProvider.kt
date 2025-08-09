package com.penguinairlines.hivetraker.data.providers

import com.penguinairlines.hivetraker.data.models.Log
interface LogProvider {
    fun addLog(hiveName: String, log: Log)

    fun getLogsForHive(hiveName: String): List<Log>

    fun removeLog(hiveName: String, log: Log)

    fun clearLogsForHive(hiveName: String)

    fun getAllLogs(): List<Log>

    fun getLogsMap(): Map<String, List<Log>>
    fun getLogByHiveAndName(hiveName: String, logName: String): Log?
}
