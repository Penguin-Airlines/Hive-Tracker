package com.penguinairlines.hivetraker.data.providers

import com.penguinairlines.hivetraker.data.models.Hive

interface HiveProvider {
    fun getHive(name: String): Hive
    fun getHives(): List<Hive>

    fun setHive(hive: Hive)
    fun setHives(hives: List<Hive>)
}