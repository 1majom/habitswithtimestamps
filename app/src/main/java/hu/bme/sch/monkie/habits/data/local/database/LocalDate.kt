package hu.bme.sch.monkie.habits.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import com.google.type.DateTime
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "dates")
data class LocalDate(
    val habitId: Int,
    val date: DateTime,
    val temp: Int
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}
@Dao
interface DatesDao {
    @Query("SELECT * FROM habits ORDER BY uid DESC LIMIT 3")
    fun getSomeDates(): Flow<List<LocalHabit>>
    @Query("SELECT * FROM habits ORDER BY uid DESC")
    fun getAllDates(): Flow<List<LocalHabit>>

    @Insert
    suspend fun insertDate(item: LocalHabit)
    @Delete
    suspend fun deleteDate(item: LocalHabit)
    @Update
    suspend fun updateDate(item: LocalHabit)
}
