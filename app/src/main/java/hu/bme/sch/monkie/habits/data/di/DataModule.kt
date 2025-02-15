package hu.bme.sch.monkie.habits.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.sch.monkie.habits.data.datasource.DateRepository
import hu.bme.sch.monkie.habits.data.datasource.DefaultDateRepository
import hu.bme.sch.monkie.habits.data.datasource.DefaultHabitRepository
import hu.bme.sch.monkie.habits.data.datasource.HabitRepository
import javax.inject.Singleton

@Module
@SuppressWarnings("unused")
@InstallIn(SingletonComponent::class)
interface DateModule {
    @Singleton
    @Binds
    fun bindsDateRepository(
        dateRepository: DefaultDateRepository
    ): DateRepository

}
@Module
@SuppressWarnings("unused")
@InstallIn(SingletonComponent::class)
interface HabitModule {
    @Singleton
    @Binds
    fun bindsHabitRepository(
        habitRepository: DefaultHabitRepository
    ): HabitRepository

}