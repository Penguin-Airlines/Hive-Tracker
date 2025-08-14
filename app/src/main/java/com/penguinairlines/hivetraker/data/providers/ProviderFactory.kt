package com.penguinairlines.hivetraker.data.providers

import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard

interface ProviderFactory {
    fun getUserProvider(): UserProvider
    fun getYardProvider(owner: User): YardProvider
    fun getHiveProvider(yard: Yard): HiveProvider
    fun getLogProvider(yard : Yard) : LogProvider
    fun getTaskProvider(yard: Yard): TaskProvider
    fun getRecordingProvider(yard: Yard): RecordingProvider
}