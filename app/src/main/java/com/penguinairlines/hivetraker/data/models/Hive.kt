package com.penguinairlines.hivetraker.data.models

import android.location.Location

data class Hive(
    val name: String,
    val yard: Yard,
    var location: Location,
    var frameCount: UInt,
    var description: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Hive) return false

        return yard === other.yard && name === other.name
    }
}
