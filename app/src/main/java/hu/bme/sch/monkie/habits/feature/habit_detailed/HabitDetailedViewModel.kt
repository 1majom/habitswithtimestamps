
package hu.bme.sch.monkie.habits.feature.habit_detailed

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.sch.monkie.habits.data.datasource.DateRepository
import hu.bme.sch.monkie.habits.data.datasource.HabitRepository
import javax.inject.Inject

@HiltViewModel
class HabitDetailedViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
    private val datesRepository: DateRepository
) : ViewModel() {

}

sealed interface HabitUiState {
    object Loading : HabitUiState
    data class Error(val throwable: Throwable) : HabitUiState
    data class Success(val data: List<String>) : HabitUiState
}
