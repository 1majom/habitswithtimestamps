
package hu.bme.sch.monkie.habits.data.local.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Entity(tableName="habits")
data class LocalHabit(
    var name: String,
    var orderId:Int
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}


@Dao
interface HabitsDao {
    @Query("SELECT * FROM habits ORDER BY orderId DESC")
    fun getAllHabits(): Flow<List<LocalHabit>>
    @Query("SELECT * FROM habits where uid = :uid")
    fun getHabit(uid:Int): Flow<List<LocalHabit>>
    @Query("SELECT * FROM habits where uid = :uid")
    suspend fun get1Habit(uid:Int): LocalHabit
    @Query("INSERT INTO habits (name, orderId) VALUES (:name, (SELECT COUNT(*) + 1 FROM habits))")
    suspend fun insertHabit(name:String)
    @Query("DELETE FROM habits WHERE uid = :uid")
    suspend fun deleteHabit(uid: Int)
    @Query("UPDATE habits SET name = :newName WHERE uid = :id")
    suspend fun updateHabit(id: Int, newName: String)
    @Query("UPDATE habits SET orderId = :orderId WHERE uid = :id")
    suspend fun updateHabitOrder(id: Int, orderId: Int)
    @Transaction
    suspend fun swapOrder(uid1: Int, uid2: Int){
        //bc not flow it can be used like this
        val habit1 = get1Habit(uid1)
        val habit2 = get1Habit(uid2)
        updateHabitOrder(uid1, habit2.orderId)
        updateHabitOrder(uid2, habit1.orderId)

    }
}
