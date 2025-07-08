package com.penguinairlines.hivetraker.data.providers

import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard

interface ProviderFactory {
    fun getUserProvider(): UserProvider
    fun getYardProvider(owner: User, name: String): YardProvider
    fun getHiveProvider(yard: Yard, name: String): HiveProvider
}