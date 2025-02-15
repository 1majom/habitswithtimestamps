
package hu.bme.sch.monkie.habits.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.sch.monkie.habits.data.local.database.AppDatabase
import hu.bme.sch.monkie.habits.data.local.database.DatesDao
import hu.bme.sch.monkie.habits.data.local.database.HabitsDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideHabitDao(appDatabase: AppDatabase): HabitsDao {
        return appDatabase.habitDao()
    }
    @Provides
    fun provideDateDao(appDatabase: AppDatabase): DatesDao {
        return appDatabase.dateDao()
    }


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "LocalHabit"
        ).build()
    }
}
