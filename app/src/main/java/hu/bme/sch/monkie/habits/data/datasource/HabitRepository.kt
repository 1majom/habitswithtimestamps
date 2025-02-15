
package hu.bme.sch.monkie.habits.data.datasource

import hu.bme.sch.monkie.habits.data.local.database.HabitsDao
import hu.bme.sch.monkie.habits.data.local.database.LocalHabit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface HabitRepository {
    suspend fun get(id: Int): Flow<List<LocalHabit>>
    suspend fun getAll(): Flow<List<LocalHabit>>
    suspend fun add(name: String)
    suspend fun delete(id:Int)
    suspend fun update(id: Int, new: String)
    suspend fun swaperoo(id: Int, id2: Int)


}

class DefaultHabitRepository @Inject constructor(
    private val habitsListDao: HabitsDao
) : HabitRepository {
    override suspend fun get(id: Int): Flow<List<LocalHabit>>{
        return habitsListDao.getHabit(id)
    }
    override suspend fun getAll(): Flow<List<LocalHabit>>{
        return habitsListDao.getAllHabits()
    }
    override suspend fun add(name: String) {
        habitsListDao.insertHabit(name)
    }
    override suspend fun delete(id:Int) {
        habitsListDao.deleteHabit(id)
    }
    override suspend fun update(id:Int, new:String) {
        habitsListDao.updateHabit(id,new)
    }
    override suspend fun swaperoo(id:Int, id2:Int) {
        habitsListDao.swapOrder(id,id2)
    }
}


class LocalHabitFakeRepository @Inject constructor() : HabitRepository {
    private var _habits = MutableSharedFlow<List<LocalHabit>>()
    val habits: Flow<List<LocalHabit>> = _habits

    override suspend fun get(id: Int): Flow<List<LocalHabit>> =
        habits.map { it.filter { habit -> habit.uid == id } }

    override suspend fun getAll(): Flow<List<LocalHabit>> = habits

    override suspend fun add(name: String) {
        val currentHabits = _habits.replayCache.firstOrNull() ?: emptyList()
        val newHabit = LocalHabit(name, currentHabits.size + 1)
        _habits.emit(currentHabits + newHabit)
    }

    override suspend fun delete(id: Int) {
        val currentHabits = _habits.replayCache.firstOrNull() ?: emptyList()
        val updatedHabits = currentHabits.filter { it.uid != id }
        _habits.emit(updatedHabits)
    }

    override suspend fun update(id: Int, new: String) {
        val currentHabits = _habits.replayCache.firstOrNull() ?: emptyList()
        val updatedHabits = currentHabits.map { if (it.uid == id) it.copy(name = new) else it }
        _habits.emit(updatedHabits)
    }

    override suspend fun swaperoo(id: Int, id2: Int) {
        val currentHabits = _habits.replayCache.firstOrNull() ?: emptyList()
        val habit1 = currentHabits.find { it.uid == id }
        val habit2 = currentHabits.find { it.uid == id2 }
        if (habit1 != null && habit2 != null) {
            val updatedHabits = currentHabits.map {
                when (it.uid) {
                    id -> it.copy(orderId = habit2.orderId)
                    id2 -> it.copy(orderId = habit1.orderId)
                    else -> it
                }
            }
            _habits.emit(updatedHabits)
        }
    }


}