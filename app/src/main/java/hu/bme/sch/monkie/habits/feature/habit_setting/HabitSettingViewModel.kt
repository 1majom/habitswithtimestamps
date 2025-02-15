
package hu.bme.sch.monkie.habits.feature.habit_setting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.sch.monkie.habits.data.datasource.HabitRepository
import javax.inject.Inject

@HiltViewModel
class HabitSettingViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {

}

sealed interface HabitSettingUiState {
    object Loading : HabitSettingUiState
    data class Error(val throwable: Throwable) : HabitSettingUiState
    data class Success(val data: List<String>) : HabitSettingUiState
}
