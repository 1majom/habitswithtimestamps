
package hu.bme.sch.monkie.habits.feature.habit

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.sch.monkie.habits.data.datasource.DateRepository
import hu.bme.sch.monkie.habits.data.datasource.HabitRepository
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
    private val dateRepository: DateRepository

) : ViewModel() {

}

sealed interface HabitUiState {
    object Loading : HabitUiState
    data class Error(val throwable: Throwable) : HabitUiState
    data class Success(val data: List<String>) : HabitUiState
}
