
package hu.bme.sch.monkie.habits.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.bme.sch.monkie.habits.feature.habit.HabitScreen
import hu.bme.sch.monkie.habits.feature.habit_detailed.HabitDetailedScreen
import hu.bme.sch.monkie.habits.feature.habit_detailed.HabitSettingScreen
import hu.bme.sch.monkie.habits.feature.habit_list.HabitListScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                HabitListScreen(
                    modifier = Modifier.padding(16.dp),
                    navigate = { route: String -> navController.navigate(route) })
            }
            composable("habit/{habitId}", arguments = listOf(navArgument("habitId") {
                type = NavType.IntType;nullable = false
            })) {
                HabitScreen(
                    modifier = Modifier.padding(16.dp),
                    navigate = { route: String -> navController.navigate(route) })
            }

            composable("habit_detailed/{habitId}", arguments = listOf(navArgument("habitId") {
                type = NavType.IntType;nullable = false
            })) { HabitDetailedScreen(modifier = Modifier.padding(16.dp)) }

            composable("settings") { HabitSettingScreen(modifier = Modifier.padding(16.dp)) }

        }
    }

