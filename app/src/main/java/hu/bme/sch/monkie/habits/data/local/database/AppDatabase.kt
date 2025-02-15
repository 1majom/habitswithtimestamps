
package hu.bme.sch.monkie.habits.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalHabit::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitsDao
    abstract fun dateDao(): DatesDao
}
