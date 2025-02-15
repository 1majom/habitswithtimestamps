
package hu.bme.sch.monkie.habits.feature.habit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.sch.monkie.habits.ui.theme.MyApplicationTheme

@Composable
fun HabitScreen(modifier: Modifier = Modifier, viewModel: HabitViewModel = hiltViewModel()) {

}

@Composable
internal fun HabitScreen(
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
        HabitScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    MyApplicationTheme {
        HabitScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}
