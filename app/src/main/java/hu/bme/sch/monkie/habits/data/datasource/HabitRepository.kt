
package hu.bme.sch.monkie.habits.data.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import hu.bme.sch.monkie.habits.data.local.database.LocalHabit
import hu.bme.sch.monkie.habits.data.local.database.HabitsDao
import javax.inject.Inject

interface HabitRepository {
    val habits: Flow<List<String>>

    suspend fun add(name: String)
    suspend fun delete(name: String)
    suspend fun update(name: String)


}

class DefaultHabitRepository @Inject constructor(
    private val habitsListDao: HabitsDao
) : HabitRepository {

    override val habits: Flow<List<String>> =
        habitsListDao.getAllHabits().map { items -> items.map { it.name } }

    override suspend fun add(name: String) {
        habitsListDao.insertHabit(LocalHabit(name = name))
    }
    override suspend fun delete(name: String) {
        habitsListDao.deleteHabit(LocalHabit(name = name))
    }
    override suspend fun update(name: String) {
        habitsListDao.updateHabit(LocalHabit(name = name))
    }
}
