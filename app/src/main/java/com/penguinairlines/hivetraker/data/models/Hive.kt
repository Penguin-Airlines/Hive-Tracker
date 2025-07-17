package com.penguinairlines.hivetraker.data.models

import android.location.Location
import kotlin.collections.emptyList
data class Hive(
    val name: String,
    val yard: Yard,
    var location: Location,
    var status: HiveStatus,
    var frameCount: UInt,
    var description: String,
    var logList: List<Log> = emptyList()

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Hive) return false

        return yard === other.yard && name === other.name
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
