package hu.bme.sch.monkie.habits.data.local.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Entity(tableName = "dates")
data class LocalTimeStamp(
    val habitId: Int,
    val date: LocalDateTime,
    val temperature: Double?,
    val visibility: Double?
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

@Dao
interface DatesDao {
    @Query("SELECT * FROM dates where habitId=:uid ORDER BY date DESC LIMIT 3")
    fun getSomeDates(uid:Int): Flow<List<LocalTimeStamp>>
    @Query("SELECT * FROM dates where habitId=:uid ORDER BY date DESC")
    fun getAllDates(uid:Int): Flow<List<LocalTimeStamp>>
    @Query("SELECT * FROM dates where uid=:uid")
    fun getDate(uid: Int): Flow<List<LocalTimeStamp>>

    @Insert
    suspend fun insertDate(theNew: LocalTimeStamp)
    @Query("DELETE FROM dates WHERE uid = :uid")
    suspend fun deleteDate(uid: Int)
    @Query("DELETE FROM dates WHERE uid = (SELECT uid FROM dates WHERE habitId=:uid ORDER BY date DESC LIMIT 1)")
    suspend fun deleteLastFromHabit(uid: Int)
    @Query("UPDATE dates SET date = :newDate, temperature = :temp, visibility= :visi WHERE uid = :uid")
    suspend fun updateDate(uid: Int, newDate: LocalDateTime, temp: Double?, visi: Double?)
}
