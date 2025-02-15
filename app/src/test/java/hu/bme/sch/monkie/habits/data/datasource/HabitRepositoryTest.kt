package hu.bme.sch.monkie.habits.data.datasource

import hu.bme.sch.monkie.habits.data.local.database.HabitsDao
import hu.bme.sch.monkie.habits.data.local.database.LocalHabit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class HabitRepositoryTest {
    lateinit var loKoto:DefaultHabitRepository

    @Before
    fun setUp(){
        loKoto = DefaultHabitRepository(FakeDao())
    }

    @After
    fun tearDown(){
    }

    @Test
    fun getBigTest()=runTest{
        println(loKoto.getAll().first().size)

        assert(loKoto.getAll().first().size == 3)
    }

    @Test
    fun addTest()=runTest{
        loKoto.add("test4")
        println(loKoto.getAll().first().size)
        assert(loKoto.getAll().first().size == 4)
    }
    @Test
    fun deleteTest()=runTest{
        loKoto.delete(1)
        assert(loKoto.getAll().first().size == 2)
    }
    @Test
    fun updateTest()=runTest{
        loKoto.update(1,"test5")
        assert(loKoto.get(1).first().first().name == "test5")
    }
    @Test
    fun getSmallTest()=runTest{
        assert(loKoto.get(1).first().first()==LocalHabit("test2",2))
    }
    @Test
    fun swapOrderTest2()=runTest{
        loKoto.getAll().collect { habit -> println(habit) }

        loKoto.swaperoo(0,1)
        loKoto.getAll().collect { habit -> println(habit) }
        assert(loKoto.get(0).first().first()==LocalHabit("test1",2))
        assert(loKoto.get(1).first().first()==LocalHabit("test2",1))

    }
}

class FakeDao:HabitsDao {
    var habits = mutableListOf(LocalHabit("test1",1), LocalHabit("test2",2), LocalHabit("test3",3))
    override fun getAllHabits(): Flow<List<LocalHabit>> {
        return flowOf(habits)
    }

    override fun getHabit(uid: Int): Flow<List<LocalHabit>> {
        return flowOf(listOf( habits[uid]))
    }

    override suspend fun get1Habit(uid: Int): LocalHabit {
        return habits[uid]
    }

    override suspend fun insertHabit(name: String) {
        habits.add(LocalHabit(name, habits.size + 1))
    }

    override suspend fun deleteHabit(uid: Int) {
        habits.removeAt(uid)
    }

    override suspend fun updateHabit(id: Int, newName: String) {
        habits[id].name=newName
    }

    override suspend fun updateHabitOrder(id: Int, orderId: Int) {
        habits[id].orderId=orderId
    }
    override suspend fun swapOrder(uid1: Int, uid2: Int){
        println(habits[uid1])
        println(habits[uid2])
        val habit1OrderId = habits[uid1].orderId
        val habit2OrderId = habits[uid2].orderId
        habits[uid1].orderId = habit2OrderId
        habits[uid2].orderId = habit1OrderId
        println(habits[uid1])
        println(habits[uid2])
    }
}