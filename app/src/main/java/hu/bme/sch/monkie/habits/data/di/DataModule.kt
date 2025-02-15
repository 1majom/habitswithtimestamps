package hu.bme.sch.monkie.habits.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.sch.monkie.habits.data.datasource.DateRepository
import hu.bme.sch.monkie.habits.data.datasource.DefaultDateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import hu.bme.sch.monkie.habits.data.datasource.HabitRepository
import hu.bme.sch.monkie.habits.data.datasource.DefaultHabitRepository
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsHabitRepository(
        habitRepository: DefaultHabitRepository
    ): HabitRepository
    @Singleton
    @Binds
    fun bindsDateRepository(
        dateRepository: DefaultDateRepository
    ): DateRepository

}

class FakeHabitRepository @Inject constructor() : HabitRepository {
    private val _habits = mutableListOf("One", "Two", "Three")
    override val habits: Flow<List<String>> = flowOf(_habits)

    override suspend fun add(name: String) {
        _habits.add(name)
    }

    override suspend fun delete(name: String) {
        _habits.remove(name)
    }

    override suspend fun update(name: String) {
        TODO("Not yet implemented")
    }
}

val fakeHabits = listOf("One", "Two", "Three")