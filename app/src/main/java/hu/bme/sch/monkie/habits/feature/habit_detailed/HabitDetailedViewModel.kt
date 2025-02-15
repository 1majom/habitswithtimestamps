
package hu.bme.sch.monkie.habits.feature.habit_detailed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.sch.monkie.habits.BuildConfig
import hu.bme.sch.monkie.habits.data.datasource.DateRepository
import hu.bme.sch.monkie.habits.data.datasource.HabitRepository
import hu.bme.sch.monkie.habits.data.local.database.LocalTimeStamp
import hu.bme.sch.monkie.habits.data.network.LatLng
import hu.bme.sch.monkie.habits.data.network.WeatherApi
import hu.bme.sch.monkie.habits.ui.common.DateTimeStampWithDiffs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class HabitDetailedViewModel @Inject constructor(
    private val datesRepository: DateRepository,
    private val habitRepository: HabitRepository,
    private val stateHandle: SavedStateHandle,
    private val api: WeatherApi

) : ViewModel() {
    val habitId = stateHandle.get<Int>("habitId")!!

    private var _uiState = MutableStateFlow(HabitDetailedUiState())
    val uiState = _uiState.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.get(habitId).collect {
                _uiState.value = _uiState.value.copy( habitName = it[0].name)
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            datesRepository.getAll(habitId).collect {
                val list= mutableListOf<DateTimeStampWithDiffs>()
                for (i in 0 until it.size-1){
                    val diffInHours= -1*it[i].date.until(it[i+1].date, java.time.temporal.ChronoUnit.HOURS)
                    val diffInMinutes= (-1*it[i].date.until(it[i+1].date, java.time.temporal.ChronoUnit.MINUTES))%60
                    list.add(DateTimeStampWithDiffs(it[i], diffInHours, diffInMinutes))
                }
                if (it.isNotEmpty()) {
                    list.add(
                        DateTimeStampWithDiffs(
                            it.last(),
                            null,
                            null
                        )
                    )
                _uiState.value = _uiState.value.copy(isLoading = false, dates = it, displayList = list)

                }

            }
        }
    }
    fun deleteDate(uid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            datesRepository.delete(uid)
        }
    }

    fun changeShowDatePicker(bool:Boolean){
        _uiState.value = _uiState.value.copy(showDatePicker = bool)
    }
    fun changeShowTimePicker(bool:Boolean){
        _uiState.value = _uiState.value.copy(showTimePicker = bool)
    }
    fun changeShowTempAndVisiPicker(bool:Boolean){
        _uiState.value = _uiState.value.copy(showTempAndVisiPicker = bool)
    }
    fun onDateSelectedBeforeDatePicker(id:Int) {
        _uiState.value = _uiState.value.copy(
            editedDate = _uiState.value.dates.first { it.uid == id },
            showDatePicker = true
        )
    }
    fun onDateTimeSelectedInDatePicker(date: LocalDateTime, lon:Double?, lat:Double?) {
        viewModelScope.launch( Dispatchers.IO) {
            var currentLatLng = LatLng(lat?:47.498,lon?:19.0399)

            val zoneId = ZoneId.systemDefault() // or ZoneId.of("ZoneId")
            val epoch = date.atZone(zoneId).toEpochSecond()
            var weather = api.getPastWeather(currentLatLng.lat.toString(), currentLatLng.lon.toString(),
                "metric", BuildConfig.API_KEY, epoch.toString())

            val uid= uiState.value.editedDate?.uid
            if (uid!=null)
                datesRepository.update(uid, date, weather.data[0].temp, weather.data[0].visibility)
        }
        _uiState.value = _uiState.value.copy(showTimePicker = false)

    }
}


data class HabitDetailedUiState (
    var isLoading:Boolean=true,
    var dates: List<LocalTimeStamp> = listOf(),
    val errorMsg: String? = null,
    var whichChart:Int=0,
    var showDatePicker: Boolean = false,
    var showTimePicker: Boolean = false,
    var showTempAndVisiPicker: Boolean = false,

    val habitName:String?=null,
    var displayList: List<DateTimeStampWithDiffs> = listOf(),

    var editedDate: LocalTimeStamp?=null,
    var tempurate: Double?=null,
    var visibility: Double?=null,
)


