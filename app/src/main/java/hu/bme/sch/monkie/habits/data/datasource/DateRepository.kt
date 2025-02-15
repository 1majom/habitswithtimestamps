package hu.bme.sch.monkie.habits.data.datasource

import hu.bme.sch.monkie.habits.data.local.database.DatesDao
import hu.bme.sch.monkie.habits.data.local.database.LocalHabit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface DateRepository {
    val dates: Flow<List<String>>

    suspend fun add(name: String)
    suspend fun delete(name: String)
    suspend fun update(name: String)


}

class DefaultDateRepository @Inject constructor(
    private val datesListDao: DatesDao
) : DateRepository {

    override val dates: Flow<List<String>> =
        datesListDao.getAllDates().map { items -> items.map { it.name } }

    override suspend fun add(name: String) {
        datesListDao.insertDate(LocalHabit(name = name))
    }
    override suspend fun delete(name: String) {
        datesListDao.deleteDate(LocalHabit(name = name))
    }
    override suspend fun update(name: String) {
        datesListDao.updateDate(LocalHabit(name = name))
    }
}
