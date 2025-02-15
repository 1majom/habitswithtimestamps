
package hu.bme.sch.monkie.habits.feature.habit

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.sch.monkie.habits.data.datasource.DateRepository
import hu.bme.sch.monkie.habits.data.datasource.HabitRepository
import javax.inject.Inject

@HiltViewModel
class HabitListViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
    private val dateRepository: DateRepository
) : ViewModel() {

}

sealed interface HabitListUiState {
    object Loading : HabitListUiState
    data class Error(val throwable: Throwable) : HabitListUiState
    data class Success(val data: List<String>) : HabitListUiState
}
