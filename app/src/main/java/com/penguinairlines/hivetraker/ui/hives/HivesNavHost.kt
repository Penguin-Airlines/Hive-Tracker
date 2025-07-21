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
import com.penguinairlines.hivetraker.ui.hives.logs.LogTemplate
@Composable
fun HivesNavHost() {
    val hiveNavController = rememberNavController();

    val currentProviderFactory: ProviderFactory = remember { TestProvider() }
    val currentUser = remember{User("", "")};
    val currentYard = remember{Yard("", currentUser)};
    val currentHiveProvider = remember { currentProviderFactory.getHiveProvider(currentYard, "") }

    NavHost(
        hiveNavController, startDestination = ListHivesScreenDestination
    ) {
        composable<ListHivesScreenDestination> {
            ListHivesScreen(
                hiveOnClick = { hive: Hive ->
                    hiveNavController.navigate(
                        HiveDetailsScreenDestination(hive.name)
                    )
                },
                addHiveOnClick = {
                    hiveNavController.navigate(
                        AddHiveScreenDestination
                    )
                },
                currentHiveProvider,
                currentUser,
                currentYard
            )

        }

        composable<HiveDetailsScreenDestination> {
            val args = it.toRoute<HiveDetailsScreenDestination>()
            val hive = currentHiveProvider.getHive(args.hiveName)
            HiveDetailScreen(
                logOnClick = { log: Log ->
                    hiveNavController.navigate(
                        LogTemplateScreenDestination(log.logName,hive.name)
                    )
                },
                hive,
                onBackClick = {
                    hiveNavController.navigateUp()
                },
                onEditClick = {
                    hiveNavController.navigate(
                        EditHiveScreenDestination(hive.name)
                    )
                }
            )
        }

        composable<EditHiveScreenDestination> {
            val args = it.toRoute<EditHiveScreenDestination>()
            val hive = currentHiveProvider.getHive(args.hiveName)
            EditHiveScreen(
                hive,
                saveHive = { updatedHive ->
                    currentHiveProvider.setHive(updatedHive)
                    hiveNavController.navigateUp()
                }
            )
        }

        composable<AddHiveScreenDestination> {
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
        composable<LogTemplateScreenDestination> {
            val args = it.toRoute<LogTemplateScreenDestination>()
            val hive = currentHiveProvider.getHive(args.hiveName)
            val log = hive.getLog(args.logName)
            LogTemplate(
                log
            )
        }
    }
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
