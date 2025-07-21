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
import com.penguinairlines.hivetraker.data.models.Log
import com.penguinairlines.hivetraker.data.providers.ProviderFactory
import com.penguinairlines.hivetraker.data.providers.test.TestProvider
import kotlinx.serialization.Serializable

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
                logOnClick = { log: Log ->
                    hiveNavController.navigate(
                        HivesDestination.Log(log.logName,hive.name)
                    )
                },
                hive,
                onBackClick = {
                    hiveNavController.navigateUp()
                },
                onEditClick = {
                    hiveNavController.navigate(
                        HivesDestination.Edit(hive.name)
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
        composable<HivesDestination.Log> {
            val args = it.toRoute<HivesDestination.Log>()
            val hive = currentHiveProvider.getHive(args.hiveName)
            val log = hive.getLog(args.logName)
            LogTemplate(
                log
            )
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
    data class Log(
        val logName: String,
        val hiveName : String
    ):HivesDestination()
}
@Serializable
object ListHivesScreenDestination

@Serializable
data class EditHiveScreenDestination (
    val hiveName: String
)

@Serializable
object AddHiveScreenDestination

@Serializable
data class HiveDetailsScreenDestination(
    val hiveName: String
)

@Serializable
data class LogTemplateScreenDestination(
    val logName: String,
    val hiveName : String
)
