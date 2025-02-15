
package hu.bme.sch.monkie.habits.feature.habit_setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.sch.monkie.habits.data.datasource.HabitRepository
import hu.bme.sch.monkie.habits.data.local.database.LocalHabit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitSettingViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
) : ViewModel() {
    private var _uiState = MutableStateFlow(HabitSettingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.getAll().collect {
                _uiState.value = _uiState.value.copy(isLoading = false, habits = it, null)
            }
        }
    }

    fun deleteHabit(uid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.delete(uid)
        }
    }

    fun sendUp(uid: Int) {

        val habit1 =uiState.value.habits.first { it.uid == uid }
        val habit2 =uiState.value.habits.firstOrNull { it.orderId == habit1.orderId+1 }

        if (habit2 != null) {
            viewModelScope.launch(Dispatchers.IO) {
                habitRepository.swaperoo(habit1.uid, habit2.uid)
            }

        }
    }
    fun sendDown(uid: Int) {
        val habit1 =uiState.value.habits.first { it.uid == uid }
        val habit2 =uiState.value.habits.firstOrNull { it.orderId == habit1.orderId-1 }

        if (habit2 != null) {
            viewModelScope.launch(Dispatchers.IO) {
                habitRepository.swaperoo(habit1.uid, habit2.uid)
            }

        }
    }
    fun newName(theNewName:String) {
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.update(uiState.value.choosenId,theNewName)
        }
    }

    fun clickOn(clicking:Boolean, uid:Int) {
            _uiState.value = uiState.value.copy(showDialog = clicking, choosenId = uid)

    }
    fun changeBool(i:Boolean){
            _uiState.value = uiState.value.copy(showDialog = i)

    }

}

data class HabitSettingUiState (
    var isLoading:Boolean=true,
    var habits: List<LocalHabit> = listOf(),
    val errorMsg: String? = null,
    var newName: String? = null,
    var showDialog:Boolean=false,
    var choosenId:Int=0
)
