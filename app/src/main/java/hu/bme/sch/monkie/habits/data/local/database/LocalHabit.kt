
package hu.bme.sch.monkie.habits.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName="habits")
data class LocalHabit(
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}


@Dao
interface HabitsDao {

    @Query("SELECT * FROM habits ORDER BY uid DESC")
    fun getAllHabits(): Flow<List<LocalHabit>>

    @Insert
    suspend fun insertHabit(item: LocalHabit)
    @Delete
    suspend fun deleteHabit(item: LocalHabit)
    @Update
    suspend fun updateHabit(item: LocalHabit)
}
