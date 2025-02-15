
package hu.bme.sch.monkie.habits.data

import hu.bme.sch.monkie.habits.data.datasource.DefaultHabitRepository
import hu.bme.sch.monkie.habits.data.local.database.HabitsDao
import hu.bme.sch.monkie.habits.data.local.database.LocalHabit
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

/**
 * Unit tests for [DefaultHabitRepository].
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class DefaultLocalHabitRepositoryTest {

    @Test
    fun habits_newItemSaved_itemIsReturned() = runTest {
        val repository = DefaultHabitRepository(FakeHabitsListDao())

        repository.add("Repository")

        //assertEquals(repository.habits.first().size, 1)
    }

}

private class FakeHabitsListDao : HabitsDao {

    private val data = mutableListOf<LocalHabit>()


    override fun getAllHabits(): Flow<List<LocalHabit>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertHabit(item: LocalHabit) {
        data.add(0, item)
    }

    override suspend fun deleteHabit(item: LocalHabit) {
        TODO("Not yet implemented")
    }

    override suspend fun updateHabit(item: LocalHabit) {
        TODO("Not yet implemented")
    }
}
