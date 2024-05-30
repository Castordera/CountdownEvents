package com.example.domain.models

data class DateHandler(
    val isInPast: Boolean = false,
    val value: Int = 0,
    val periodType: TimePeriod = TimePeriod.NONE,
    val isToday: Boolean = false,
)

enum class TimePeriod {
    NONE,
    YEAR,
    WEEK,
    DAY,
    HOUR,
    MINUTE,
}
