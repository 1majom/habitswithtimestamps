package hu.bme.sch.monkie.habits.ui.model

import com.google.type.DateTime

data class Habit(
    val name:String,
    val dates:List<Date>
)

data class Date(
    val date: DateTime,
    val temp:Int
)