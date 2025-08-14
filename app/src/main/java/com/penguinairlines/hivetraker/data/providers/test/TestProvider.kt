package com.penguinairlines.hivetraker.data.providers.test

import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.providers.HiveProvider
import com.penguinairlines.hivetraker.data.providers.ProviderFactory
import com.penguinairlines.hivetraker.data.providers.TaskProvider
import com.penguinairlines.hivetraker.data.providers.UserProvider
import com.penguinairlines.hivetraker.data.providers.YardProvider

class TestProvider: ProviderFactory {
    override fun getUserProvider(): UserProvider {
        TODO("Not yet implemented")
    }

    override fun getYardProvider(
        owner: User
    ): YardProvider {
        TODO("Not yet implemented")
    }

    override fun getHiveProvider(
        yard: Yard
    ): HiveProvider {
        TestHiveProvider.yard = yard
        return TestHiveProvider
    }

    override fun getTaskProvider(yard: Yard): TaskProvider {
        return TestTaskProvider
    }

    override fun getRecordingProvider(yard: Yard): RecordingProvider {
        return TestTaskProvider
    }
}