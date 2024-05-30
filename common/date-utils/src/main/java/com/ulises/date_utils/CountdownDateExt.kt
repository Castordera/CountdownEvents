package com.ulises.date_utils

import com.example.domain.enums.DateDisplayType
import com.example.domain.models.CountdownDate
import com.example.domain.models.DateHandler
import com.example.domain.models.TimePeriod
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.abs

val CountdownDate.remainingTime: DateHandler
    get() {
        return runCatching {
            when (dateDisplayType) {
                DateDisplayType.REGULAR -> handleRegularDisplayDate()
                DateDisplayType.WEEKLY -> handleWeeklyDisplayDate()
            }
        }.getOrElse {
            DateHandler()
        }
    }

private fun CountdownDate.handleWeeklyDisplayDate(): DateHandler {
    val today = LocalDateTime.now()
    val duration = Duration.between(today, dateToCountdown)
    return DateHandler(
        isInPast = duration.isNegative,
        value = abs((duration.toDays() / 7)).toInt(),
        periodType = TimePeriod.WEEK,
        isToday = duration.toDays() == 0L && duration.isNegative,
    )
}

private fun CountdownDate.handleRegularDisplayDate(): DateHandler {
    val today = LocalDateTime.now()
    val formatted = dateToCountdown
    val duration = Duration.between(today, formatted)
    val timePeriod = when {
        abs(duration.toDays()) > 365 -> ChronoUnit.YEARS.between(
            today,
            formatted
        ) to TimePeriod.YEAR

        abs(duration.toHours()) > 24 -> duration.toDays() to TimePeriod.DAY
        abs(duration.toMinutes()) > 60 -> duration.toHours() to TimePeriod.HOUR
        else -> duration.toMinutes() to TimePeriod.MINUTE
    }
    return DateHandler(
        isInPast = duration.isNegative,
        value = abs(timePeriod.first).toInt(),
        periodType = timePeriod.second,
        isToday = duration.toDays() == 0L && duration.isNegative,
    )
}

val DateHandler.getQtyPast: Int
    get() = if (isInPast) 1 else 0