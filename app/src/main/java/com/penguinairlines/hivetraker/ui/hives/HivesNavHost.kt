package com.penguinairlines.hivetraker.ui.hives

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.penguinairlines.hivetraker.data.models.Hive
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.providers.test.TestProvider
import com.penguinairlines.hivetraker.ui.hives.logs.AddLogScreen
import com.penguinairlines.hivetraker.ui.hives.logs.LogTemplate
import kotlinx.serialization.Serializable

@Composable
fun HivesNavHost(
    providerFactory: TestProvider,
    currentYard: Yard,
) {
    val hiveNavController = rememberNavController()

    val hiveProvider = remember { providerFactory.getHiveProvider(currentYard) }
    val logProvider = remember { providerFactory.getLogProvider(currentYard) }
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
                hiveProvider,
                currentYard
            )

        }

        composable<HivesDestination.Details> {
            val args = it.toRoute<HivesDestination.Details>()
            val hive = hiveProvider.getHive(args.hiveName)

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
                },
                onAddLogClick = { hive -> hiveNavController.navigate(HivesDestination.AddLog(hive.name)) }
            )
        }

        composable<HivesDestination.Edit> {
            val args = it.toRoute<HivesDestination.Edit>()
            val hive = hiveProvider.getHive(args.hiveName)
            EditHiveScreen(
                hive,
                saveHive = { updatedHive ->
                    hiveProvider.setHive(updatedHive)
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
                    hiveProvider.setHive(hiveData)
                    hiveNavController.navigateUp()
                }

            )
        }
        composable<HivesDestination.LogTemplate> {
            val args = it.toRoute<HivesDestination.LogTemplate>()
            val hive = hiveProvider.getHive(args.hiveName)
            val log = logProvider.getLogByHiveAndName(hive.name,args.logName) ?: throw NoSuchElementException()

            LogTemplate(log = log, onLogBackClick = {hiveNavController.navigateUp()})
        }
        composable<HivesDestination.AddLog> {
            val args = it.toRoute<HivesDestination.AddLog>()
            val hive = hiveProvider.getHive(args.hiveName)


            AddLogScreen( hive.name,
                onSaveClick = {
                log -> logProvider.addLog(hive.name,log)
                hiveNavController.navigateUp()
                              },
                onLogBackClick = {hiveNavController.navigateUp()})
        }
    }
}

sealed class HivesDestination() {
    @kotlinx.serialization.Serializable
    object List: HivesDestination()
    @kotlinx.serialization.Serializable
    data class Details(
        val hiveName: String
    ): HivesDestination()
    @kotlinx.serialization.Serializable
    data class Edit(
        val hiveName: String
    ): HivesDestination()
    @kotlinx.serialization.Serializable
    object Add: HivesDestination()
    @kotlinx.serialization.Serializable
    data class LogTemplate(
        val logName: String,
        val hiveName: String
    ) : HivesDestination()
    @kotlinx.serialization.Serializable
    data class AddLog(
        val hiveName: String
    ): HivesDestination()
}
