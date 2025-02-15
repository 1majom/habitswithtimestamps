package hu.bme.sch.monkie.habits.ui.common

import hu.bme.sch.monkie.habits.data.local.database.LocalTimeStamp


data class DateTimeStampWithDiffs (
    val timestamp: LocalTimeStamp,
    val diffInHours: Long?,
    val diffInMinutes: Long?
)