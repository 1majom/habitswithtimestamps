
package hu.bme.sch.monkie.habits.feature.habit_detailed

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.sch.monkie.habits.ui.theme.MyApplicationTheme

@Composable
fun HabitDetailedScreen(modifier: Modifier = Modifier, viewModel: HabitDetailedViewModel = hiltViewModel()) {

}

@Composable
internal fun HabitDetailedScreen(
    items: List<String>,
    onSave: (name: String) -> Unit,
    modifier: Modifier = Modifier
) {

}

// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        HabitDetailedScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    MyApplicationTheme {
        HabitDetailedScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}