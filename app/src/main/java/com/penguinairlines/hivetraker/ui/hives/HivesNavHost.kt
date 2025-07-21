package com.penguinairlines.hivetraker.ui.hives

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.penguinairlines.hivetraker.data.models.Hive
import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.providers.ProviderFactory
import com.penguinairlines.hivetraker.data.providers.test.TestProvider
import kotlinx.serialization.Serializable
import com.penguinairlines.hivetraker.ui.hives.logs.LogTemplate

@Composable
fun HivesNavHost() {
    val hiveNavController = rememberNavController()

    val currentProviderFactory: ProviderFactory = remember { TestProvider() }
    val currentUser = remember{User("", "")}
    val currentYard = remember{Yard("", currentUser)}
    val currentHiveProvider = remember { currentProviderFactory.getHiveProvider(currentYard) }

    NavHost(
        hiveNavController, startDestination = HivesDestination.List
    ) {
        composable<HivesDestination.List> {
            ListHivesScreen(
                hiveOnClick = { hive: Hive ->
                    hiveNavController.navigate(
                        HivesDestination.Details(hive.name)
                    )
                },
                addHiveOnClick = {
                    hiveNavController.navigate(
                        HivesDestination.Add
                    )
                },
                currentHiveProvider,
                currentUser,
                currentYard
            )

        }

        composable<HivesDestination.Details> {
            val args = it.toRoute<HivesDestination.Details>()
            val hive = currentHiveProvider.getHive(args.hiveName)

            HiveDetailScreen(
                hiveData = hive,
                onBackClick = { hiveNavController.navigateUp() },
                onEditClick = {
                    hiveNavController.navigate(HivesDestination.Edit(hive.name))
                },
                logOnClick = { log ->
                    hiveNavController.navigate(
                        HivesDestination.LogTemplate(logName = log.logName, hiveName = hive.name)
                    )
                }
            )
        }

        composable<HivesDestination.Edit> {
            val args = it.toRoute<HivesDestination.Edit>()
            val hive = currentHiveProvider.getHive(args.hiveName)
            EditHiveScreen(
                hive,
                saveHive = { updatedHive ->
                    currentHiveProvider.setHive(updatedHive)
                    hiveNavController.navigateUp()
                }
            )
        }

        composable<HivesDestination.Add> {
            AddHiveScreen(
                onBackClick = {
                    hiveNavController.navigateUp()
                },
                onSaveClick = { hiveData ->
                    currentHiveProvider.setHive(hiveData)
                    hiveNavController.navigateUp()
                }

            )
        }
        composable<HivesDestination.LogTemplate> {
            val args = it.toRoute<HivesDestination.LogTemplate>()
            val hive = currentHiveProvider.getHive(args.hiveName)
            val log = hive.getLog(args.logName)


            LogTemplate(log = log)
        }
    }
}

sealed class HivesDestination() {
    @Serializable
    object List: HivesDestination()
    @Serializable
    data class Details(
        val hiveName: String
    ): HivesDestination()
    @Serializable
    data class Edit(
        val hiveName: String
    ): HivesDestination()
    @Serializable
    object Add: HivesDestination()
    @Serializable
    data class LogTemplate(
        val logName: String,
        val hiveName: String
    ) : HivesDestination()
}
