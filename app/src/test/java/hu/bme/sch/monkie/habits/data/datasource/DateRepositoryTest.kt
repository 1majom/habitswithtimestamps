package hu.bme.sch.monkie.habits.data.datasource

import hu.bme.sch.monkie.habits.data.local.database.DatesDao
import hu.bme.sch.monkie.habits.data.local.database.LocalTimeStamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class DateRepositoryTest {
    lateinit var loKoto:DefaultDateRepository

    @Before
    fun setUp(){
        loKoto = DefaultDateRepository(FakeDateDao())
    }


    @Test
    fun getBigTest()=runTest{
        println(loKoto.getAll(1).first().size)

        assert(loKoto.getAll(1).first().size == 4)
    }
    @Test
    fun getBigTest3()=runTest{
        println(loKoto.get3(1).first().size)

        assert(loKoto.get3(1).first().size == 3)
    }
    @Test
    fun getSmallTest()=runTest{
        println(LocalTimeStamp(1, LocalDateTime.now(), 1.0, 1.0))
        println(loKoto.get(0).first().first())
        assert(loKoto.get(0).first().first()==LocalTimeStamp(1, LocalDateTime.of(2021,12,12,11,11,0,0), 1.0, 1.0))
    }
    @Test
    fun addTest()=runTest{
        loKoto.add(3, 1.0, 1.0)
        println(loKoto.getAll(3).first().size)
        assert(loKoto.getAll(3).first().size == 1)
    }
    @Test
    fun deleteTest()=runTest{
        loKoto.delete(1)
        assert(loKoto.getAll(1).first().size == 3)
    }
    @Test
    fun updateTest()=runTest{
        loKoto.update(4, LocalDateTime.of(2020,12,12,11,11,0,0),
            3.0, 4.0)
        assert(loKoto.getAll(2).first().first() == LocalTimeStamp(2, LocalDateTime.of(2020,12,12,11,11,0,0),
            3.0, 4.0))
    }

    @Test
    fun deleteLastTest()=runTest{
        val latestBeforeDeletion = loKoto.getAll(1).first().maxByOrNull { it.date }
        loKoto.deleteLastFromHabit(1)
        val latestAfterDeletion = loKoto.getAll(1).first().maxByOrNull { it.date }
        assert(latestBeforeDeletion != latestAfterDeletion)
    }

}

class FakeDateDao: DatesDao {
    var dates = mutableListOf(
        LocalTimeStamp(1, LocalDateTime.of(2021,12,12,11,11,0,0), 1.0, 1.0),
        LocalTimeStamp(1, LocalDateTime.now().minusDays(1), 2.0, 2.0),
        LocalTimeStamp(1, LocalDateTime.now().minusDays(2), 3.0, 3.0),
        LocalTimeStamp(1, LocalDateTime.now().minusDays(3), 3.0, 3.0),
        LocalTimeStamp(2, LocalDateTime.now(), 1.0, 1.0),
    )
    override fun getSomeDates(uid: Int): Flow<List<LocalTimeStamp>> {
        return flowOf(dates.filter { it.habitId == uid }.sortedByDescending { it.date }.take(3))
    }

    override fun getAllDates(uid: Int): Flow<List<LocalTimeStamp>> {
        return flowOf(dates.filter { it.habitId == uid })
    }

    override fun getDate(uid: Int): Flow<List<LocalTimeStamp>> {
        return flowOf(listOf( dates[uid]))
    }

    override suspend fun insertDate(theNew: LocalTimeStamp) {
        dates.add(theNew)
    }

    override suspend fun deleteDate(uid: Int) {
        dates.removeAt(uid)
    }

    override suspend fun deleteLastFromHabit(uid: Int) {
        dates.filter { it.habitId == uid }.maxByOrNull { it.date }?.let { dates.remove(it) }
    }

    override suspend fun updateDate(
        uid: Int,
        newDate: LocalDateTime,
        temp: Double?,
        visi: Double?
    ) {
        dates[uid] = dates[uid].copy(date = newDate, temperature = temp, visibility = visi)
    }


}