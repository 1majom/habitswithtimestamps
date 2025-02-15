
package hu.bme.sch.monkie.habits.feature.habit_list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.sch.monkie.habits.feature.habit.HabitListViewModel
import hu.bme.sch.monkie.habits.ui.theme.MyApplicationTheme

@Composable
fun HabitListScreen(modifier: Modifier = Modifier, viewModel: HabitListViewModel = hiltViewModel()) {

}

@Composable
internal fun HabitListScreen(
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
        HabitListScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    MyApplicationTheme {
        HabitListScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}
