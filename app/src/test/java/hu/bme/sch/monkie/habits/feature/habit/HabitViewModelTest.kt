package hu.bme.sch.monkie.habits.feature.habit

class HabitViewModelTest {
//
//    private lateinit var savedStateHandle: SavedStateHandle
//    private lateinit var viewModel: HabitViewModel
//    private lateinit var LocalHabitFakeRepository: LocalHabitFakeRepository
//    private lateinit var LocalDateFakeRepository: LocalDateFakeRepository
//
//
//    @Before
//    fun setUp() {
//
//        savedStateHandle = SavedStateHandle()
//        LocalHabitFakeRepository = LocalHabitFakeRepository()
//        LocalDateFakeRepository = LocalDateFakeRepository()
//        viewModel = HabitViewModel(
//            dateRepository = LocalDateFakeRepository,
//            habitRepository = LocalHabitFakeRepository,
//            stateHandle = savedStateHandle
//        )
//    }
//
//    @Test
//    fun uiState_initiallyLoading_FetchData() = runTest {
//    }
//    }
//
//}
//
//class LocalHabitFakeRepository @Inject constructor() : HabitRepository {
//    private var _habits = MutableSharedFlow<List<LocalHabit>>()
//    val habits: Flow<List<LocalHabit>> = _habits
//
//    override suspend fun get(id: Int): Flow<List<LocalHabit>> =
//        habits.map { it.filter { habit -> habit.uid == id } }
//
//    override suspend fun getAll(): Flow<List<LocalHabit>> = habits
//
//    override suspend fun add(name: String) {
//        val currentHabits = _habits.replayCache.firstOrNull() ?: emptyList()
//        val newHabit = LocalHabit(name, currentHabits.size + 1)
//        _habits.emit(currentHabits + newHabit)
//    }
//
//    override suspend fun delete(id: Int) {
//        val currentHabits = _habits.replayCache.firstOrNull() ?: emptyList()
//        val updatedHabits = currentHabits.filter { it.uid != id }
//        _habits.emit(updatedHabits)
//    }
//
//    override suspend fun update(id: Int, new: String) {
//        val currentHabits = _habits.replayCache.firstOrNull() ?: emptyList()
//        val updatedHabits = currentHabits.map { if (it.uid == id) it.copy(name = new) else it }
//        _habits.emit(updatedHabits)
//    }
//
//    override suspend fun swaperoo(id: Int, id2: Int) {
//        val currentHabits = _habits.replayCache.firstOrNull() ?: emptyList()
//        val habit1 = currentHabits.find { it.uid == id }
//        val habit2 = currentHabits.find { it.uid == id2 }
//        if (habit1 != null && habit2 != null) {
//            val updatedHabits = currentHabits.map {
//                when (it.uid) {
//                    id -> it.copy(orderId = habit2.orderId)
//                    id2 -> it.copy(orderId = habit1.orderId)
//                    else -> it
//                }
//            }
//            _habits.emit(updatedHabits)
//        }
//    }
//
//
//}
//
//class LocalDateFakeRepository : DateRepository {
//    override suspend fun get3(id: Int): Flow<List<LocalTimeStamp>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getAll(id: Int): Flow<List<LocalTimeStamp>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun delete(id: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun deleteLastFromHabit(habitId: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun update(id: Int, new: LocalDateTime, temp: Double?, visi: Double?) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun add(theHabit: Int, temp: Double, visibility: Double) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun get(id: Int): Flow<List<LocalTimeStamp>> {
//        TODO("Not yet implemented")
//    }
//
}
