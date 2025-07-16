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
                        EditHiveScreenDestination(hive.name)
                    )
                },
                currentHiveProvider,
                currentUser,
                currentYard
            )

        }

        composable<EditHiveScreenDestination> {
            val args = it.toRoute<EditHiveScreenDestination>()
            val hive = currentHiveProvider.getHive(args.hiveName)
            EditHiveScreen(hive)

        }
    }
}

@Serializable
object ListHivesScreenDestination

@Serializable
data class EditHiveScreenDestination (
    val hiveName: String
)
