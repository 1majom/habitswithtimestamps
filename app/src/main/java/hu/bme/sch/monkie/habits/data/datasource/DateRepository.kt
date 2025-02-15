package hu.bme.sch.monkie.habits.data.datasource

import hu.bme.sch.monkie.habits.data.local.database.DatesDao
import hu.bme.sch.monkie.habits.data.local.database.LocalTimeStamp
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject


interface DateRepository {
    suspend fun get3(id: Int): Flow<List<LocalTimeStamp>>
    suspend fun getAll(id: Int): Flow<List<LocalTimeStamp>>
    suspend fun delete(id: Int)
    suspend fun deleteLastFromHabit(habitId: Int)


    suspend fun update(id: Int, new:LocalDateTime, temp: Double?,visi: Double?)
    suspend fun add(theHabit: Int, temp:Double, visibility:Double)
    suspend fun get(id: Int): Flow<List<LocalTimeStamp>>
}

class DefaultDateRepository @Inject constructor(
    private val datesListDao: DatesDao,

) : DateRepository {

    override suspend fun add(theHabit:Int, temp:Double, visibility:Double) {
        val currentTime = LocalDateTime.now()
        datesListDao.insertDate(LocalTimeStamp(theHabit, currentTime, temp, visibility))
    }
    override suspend fun get3(id: Int): Flow<List<LocalTimeStamp>> {
        return datesListDao.getSomeDates(id)
    }
    override suspend fun get(id: Int): Flow<List<LocalTimeStamp>> {
        return datesListDao.getDate(id)
    }
    override suspend fun getAll(id: Int): Flow<List<LocalTimeStamp>> {
        return datesListDao.getAllDates(id)
    }
    override suspend fun delete(id: Int) {
        datesListDao.deleteDate(id)
    }
    override suspend fun deleteLastFromHabit(id: Int) {

        datesListDao.deleteLastFromHabit(id)
    }
    override suspend fun update(id: Int, new:LocalDateTime, temp: Double?,visi: Double?) {
        datesListDao.updateDate(id, new, temp, visi)
    }
}

class LocalDateFakeRepository : DateRepository {
    override suspend fun get3(id: Int): Flow<List<LocalTimeStamp>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(id: Int): Flow<List<LocalTimeStamp>> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLastFromHabit(habitId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun update(id: Int, new: LocalDateTime, temp: Double?, visi: Double?) {
        TODO("Not yet implemented")
    }

    override suspend fun add(theHabit: Int, temp: Double, visibility: Double) {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: Int): Flow<List<LocalTimeStamp>> {
        TODO("Not yet implemented")
    }

}
