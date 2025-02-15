
package hu.bme.sch.monkie.habits.feature.habit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.bme.sch.monkie.habits.data.local.database.LocalTimeStamp
import hu.bme.sch.monkie.habits.ui.common.BarchartWithSolidBars
import hu.bme.sch.monkie.habits.ui.common.DateTimeStampWithDiffs
import hu.bme.sch.monkie.habits.ui.common.DisplayDateTimeStampWithDiffs
import hu.bme.sch.monkie.habits.ui.common.countDatesByDayOfWeek
import hu.bme.sch.monkie.habits.ui.common.countDatesByHourOfDay
import hu.bme.sch.monkie.habits.ui.theme.AppTheme
import java.time.LocalDateTime

@Composable
fun HabitScreen(modifier: Modifier = Modifier, viewModel: HabitViewModel = hiltViewModel(),navigate: (String) -> Unit) {
    val currentState by viewModel.uiState.collectAsStateWithLifecycle()
    HabitScreen(
        dates = currentState.dates,
        whichChart=currentState.whichChart,
        modifier = modifier,
        habitName= currentState.habitName,
        habitId= currentState.habitId,
        changeChart = viewModel::changeChart,
        navigate=navigate,
        display=currentState.displayList,
        averageTemp=currentState.averageTemp,
        averageVisibility=currentState.averageVisibility,
        averageTimesPerDay=currentState.averageTimesPerDay,
        averageTimesPerWeek=currentState.averageTimesPerWeek
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HabitScreen(
    dates: List<LocalTimeStamp>,
    whichChart:Int,
    modifier: Modifier = Modifier,
    habitName:String,
    habitId:Int,
    changeChart:()->Unit,
    navigate:(String) -> Unit,
    display:List<DateTimeStampWithDiffs>,
    averageTemp: Double?,
    averageVisibility: Double?,
    averageTimesPerDay: Double?,
    averageTimesPerWeek: Double?
    ) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Habit detailed view - $habitName") },
            )
        }
    ) {
        Column(Modifier.padding(it)) {
            if (dates.isEmpty()) {
                Text(text = "No entries yet", textAlign = TextAlign.Center)
            }
            else {
                Text(text = "Previous entries")
                LazyColumn() {
                    items(display) { item ->
                        DisplayDateTimeStampWithDiffs(item = item)
                    }
                }
                Button(onClick = {navigate("habit_detailed/$habitId")  }) {
                    Text(text = "All entries, editing")
                }
                Button(onClick = changeChart) {
                    Text(text = "Change chart")
                }

                val dates2: List<LocalDateTime> = dates.map { it.date }

                if (whichChart == 0) {
                    val dayOfWeekData = countDatesByDayOfWeek(dates2)
                    BarchartWithSolidBars(Modifier.padding(it),
                        labels = dayOfWeekData.keys.map { it.name },
                        numbers = dayOfWeekData.values.map { it.toFloat() }
                    )
                } else if (whichChart == 1) {
                    val hourOfDayData = countDatesByHourOfDay(dates2)
                    BarchartWithSolidBars(Modifier.padding(it),
                        labels = hourOfDayData.keys.map { it.toString() },
                        numbers = hourOfDayData.values.map { it.toFloat() }
                    )
                }else if (whichChart == 2) {
                    Column {
                        Text(text = "Average Temp.: $averageTemp")
                        Text(text = "Average Visibility: $averageVisibility")
                        Text(text = "Average Times Per Day: $averageTimesPerDay")
                        Text(text = "Average Times Per Week: $averageTimesPerWeek")
                    }
                }
                }
            }
        }
    }



// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AppTheme {
        HabitScreen(
            dates = listOf(LocalTimeStamp(1, LocalDateTime.now(), 0.0,0.0)),
            whichChart = 0,
            habitName = "Test Habit",
            habitId = 1,
            changeChart = {},
            navigate = {},
            display = listOf(DateTimeStampWithDiffs(LocalTimeStamp(1, LocalDateTime.now(), 0.0,0.0), 0, 0)),
            averageTemp = null,
            averageVisibility = null,
            averageTimesPerDay = null,
            averageTimesPerWeek = null
        )
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    AppTheme {
        HabitScreen(
            dates = listOf(LocalTimeStamp(1, LocalDateTime.now(), 0.0,0.0)),
            whichChart = 0,
            habitName = "Test Habit",
            habitId = 1,
            changeChart = {},
            navigate = {},
            display = listOf(DateTimeStampWithDiffs(LocalTimeStamp(1, LocalDateTime.now(), 0.0,0.0), 0, 0)),
            averageTemp = null,
            averageVisibility = null,
            averageTimesPerDay = null,
            averageTimesPerWeek = null
        )
    }
}
