
package hu.bme.sch.monkie.habits.feature.habit_detailed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import hu.bme.sch.monkie.habits.ui.common.DateTimePicker
import hu.bme.sch.monkie.habits.ui.common.DateTimeStampWithDiffs
import hu.bme.sch.monkie.habits.ui.common.DisplayDateTimeStampWithDiffs
import hu.bme.sch.monkie.habits.ui.theme.AppTheme
import java.time.LocalDateTime

@Composable
fun HabitDetailedScreen(modifier: Modifier = Modifier, viewModel: HabitDetailedViewModel = hiltViewModel()) {
    val currentState by viewModel.uiState.collectAsStateWithLifecycle()

    HabitDetailedScreen(
        dates = currentState.dates,
        modifier = modifier,
        showDatePicker = currentState.showDatePicker,
        showTimePicker = currentState.showTimePicker,
        editedDate= currentState.editedDate,
        onDateSelectedBeforeDatePicker = viewModel::onDateSelectedBeforeDatePicker,
        changeShowDatePicker = viewModel::changeShowDatePicker,
        deleteDate = viewModel::deleteDate,
        onDateTimeSelected = viewModel::onDateTimeSelectedInDatePicker,
        changeShowTimePicker =viewModel::changeShowTimePicker,
        changeShowTempAndVisiPicker =viewModel::changeShowTempAndVisiPicker,
        showTempAndVisiPicker =currentState.showTempAndVisiPicker,
        display=currentState.displayList,
        habitName=currentState.habitName
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HabitDetailedScreen(
    dates: List<LocalTimeStamp>,
    modifier: Modifier = Modifier,
    editedDate:LocalTimeStamp?,
    showDatePicker:Boolean,
    showTimePicker:Boolean,
    onDateTimeSelected:(LocalDateTime, Double?, Double?) -> Unit,
    deleteDate:(uid:Int) -> Unit,
    onDateSelectedBeforeDatePicker:(Int) -> Unit,
    changeShowDatePicker:(Boolean) -> Unit,
    changeShowTimePicker:(Boolean) -> Unit,
    changeShowTempAndVisiPicker:(Boolean) -> Unit,
    showTempAndVisiPicker:Boolean,
    display:List<DateTimeStampWithDiffs>,
    habitName:String?,
    ) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All dates of $habitName") },
            )
        }

    ) {//this is for educational purposes
        scaffoldPaddingValues ->

        if (display.isEmpty()) {
            Column(modifier=Modifier.padding(scaffoldPaddingValues)) {
                Text(text = "No entries yet", textAlign = TextAlign.Center)
            }
        } else {
            DateTimePicker(
                dateTimeInstant =editedDate?.date?:LocalDateTime.now(),
                onDateTimeSelected = onDateTimeSelected,
                showDatePicker =showDatePicker ,
                showTimePicker = showTimePicker,
                changeShowDatePicker =changeShowDatePicker ,
                changeShowTimePicker =changeShowTimePicker,
                changeTempAndVisiPicker = changeShowTempAndVisiPicker,
                showTempAndVisiPicker = showTempAndVisiPicker

            )
            LazyColumn(modifier = Modifier.padding(scaffoldPaddingValues)) {
                items(display) { item ->
                    DisplayDateTimeStampWithDiffs(
                        item = item,
                        deleteDate, onDateSelectedBeforeDatePicker,
                        changeShowDatePicker)
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
        HabitDetailedScreen()
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    AppTheme {
        HabitDetailedScreen()
    }
}