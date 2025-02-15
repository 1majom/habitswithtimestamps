
package hu.bme.sch.monkie.habits.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LocalHabit::class,LocalTimeStamp::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitsDao
    abstract fun dateDao(): DatesDao
}
