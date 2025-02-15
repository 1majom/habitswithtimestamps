
package hu.bme.sch.monkie.habits.feature.habit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.roundTwoDecimal
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.sch.monkie.habits.data.datasource.DateRepository
import hu.bme.sch.monkie.habits.data.datasource.HabitRepository
import hu.bme.sch.monkie.habits.data.local.database.LocalTimeStamp
import hu.bme.sch.monkie.habits.ui.common.DateTimeStampWithDiffs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HabitViewModel @Inject constructor(
    private val dateRepository: DateRepository,
    private val habitRepository: HabitRepository,
    private val stateHandle: SavedStateHandle
    ) : ViewModel() {
    private var _uiState = MutableStateFlow(HabitUiState())
    val uiState = _uiState.asStateFlow()



    init {
        val habitId = stateHandle.get<Int>("habitId")!!
        _uiState.value=_uiState.value.copy(isLoading = false, habitId = habitId)

        viewModelScope.launch( Dispatchers.IO) {
            habitRepository.get(habitId).collect{
                _uiState.value=_uiState.value.copy(isLoading = false, habitName = it[0].name)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.get(habitId).collect {
                _uiState.value = _uiState.value.copy( habitName = it[0].name)
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            dateRepository.getAll(habitId).collect {
                var theCount = 0

                when (it.size) {
                    1 -> theCount = 1
                    2 -> theCount = 2
                    0 -> theCount = 0
                    else -> theCount = 3
                }

                val list= mutableListOf<DateTimeStampWithDiffs>()
                for (i in 0 until theCount-1){
                    val diffInHours= -1*it[i].date.until(it[i+1].date, java.time.temporal.ChronoUnit.HOURS)
                    val diffInMinutes= (-1*it[i].date.until(it[i+1].date, java.time.temporal.ChronoUnit.MINUTES))%60
                    list.add(DateTimeStampWithDiffs(it[i], diffInHours, diffInMinutes))
                }
                if (0<theCount) {
                    list.add(
                        DateTimeStampWithDiffs(
                            it.get(theCount-1),
                            null,
                            null
                        )
                    )
                    _uiState.value = _uiState.value.copy(isLoading = false, dates = it, displayList = list)

                }

            }
        }
    }
    fun changeChart(){
        var num=uiState.value.whichChart
        num=(num+1)%3
        viewModelScope.launch( Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(whichChart = num)
        }
        if (num==2){
            viewModelScope.launch( Dispatchers.IO) {
                var sumTemp=0.0
                var sumVisibility=0.0
                var countWeather=0
                var dates2= uiState.value.dates.map { it.date }

                val minDate = dates2.minOrNull()
                val maxDate = dates2.maxOrNull()
                var averagePerDay = 0.0
                var averagePerWeek = 0.0
                if (minDate != null && maxDate != null) {
                    val totalDays = java.time.temporal.ChronoUnit.DAYS.between(minDate, maxDate) + 1
                    val totalWeeks = java.time.temporal.ChronoUnit.WEEKS.between(minDate, maxDate) + 1

                    averagePerDay = dates2.size.toDouble() / totalDays
                    averagePerWeek = dates2.size.toDouble() / totalWeeks

                }
                for (i in uiState.value.dates){
                    if (i.temperature!=null&&i.visibility!=null) {
                        sumTemp += i.temperature
                        sumVisibility += i.visibility
                        countWeather++
                    }

                }
                _uiState.value = _uiState.value.copy(averageTemp = sumTemp/countWeather,
                    averageVisibility = sumVisibility/countWeather,
                    averageTimesPerDay = averagePerDay.roundTwoDecimal(),
                    averageTimesPerWeek = averagePerWeek.roundTwoDecimal())
            }
        }
    }

}

data class HabitUiState (
    var isLoading:Boolean=true,
    var dates: List<LocalTimeStamp> = listOf(),
    var habitName:  String="",
    var habitId:  Int=1,
    var errorMsg: String? = null,
    var whichChart:Int=0,
    var displayList: List<DateTimeStampWithDiffs> = listOf(),
    var averageTemp: Double=0.0,
    var averageVisibility: Double=0.0,
    var averageTimesPerDay: Double=0.0,
    var averageTimesPerWeek: Double=0.0

)
