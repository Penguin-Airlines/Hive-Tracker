package com.penguinairlines.hivetraker.data.providers

import com.penguinairlines.hivetraker.data.models.Log

interface LogProvider {
    fun getLog(name: String): Log
    fun getLogs(): List<Log>

    fun setLog(log: Log)
    fun setLogs(logs: List<Log>)
}
