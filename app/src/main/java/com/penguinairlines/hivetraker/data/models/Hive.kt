package com.penguinairlines.hivetraker.data.models

import android.location.Location
data class Hive(
    var name: String,
    var yard: Yard,
    var location: Location,
    var status: HiveStatus,
    var frameCount: UInt,
    var description: String,
    var logList: MutableList<Log> = mutableListOf()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Hive) return false

        return yard === other.yard && name === other.name
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
    fun addLog(newLog : Log): Boolean{
        logList.add(newLog)
        return true
    }
    fun getLog(logName : String): Log{
        logList.forEach() { log -> if(logName.equals(log.logName))
            return log
        }
        throw NoSuchElementException()
    }
}
