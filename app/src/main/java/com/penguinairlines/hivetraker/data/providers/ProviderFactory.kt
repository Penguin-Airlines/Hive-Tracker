package com.penguinairlines.hivetraker.data.providers

import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard

interface ProviderFactory {
    fun getUserProvider(): UserProvider
    fun getYardProvider(owner: User): YardProvider
    fun getHiveProvider(yard: Yard): HiveProvider

    fun getTaskProvider(yard: Yard): TaskProvider
}