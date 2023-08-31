package com.ulises.event_detail.ui

import com.example.domain.enums.DateDisplayType
import com.example.domain.models.CountdownDate
import com.example.domain.models.DateHandler
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
            DateHandler(false, "N/A", "N/A")
        }
    }

private fun CountdownDate.handleWeeklyDisplayDate(): DateHandler {
    val today = LocalDateTime.now()
    val duration = Duration.between(today, dateToCountdown)
    return DateHandler(
        isInPast = duration.isNegative,
        value = abs((duration.toDays() / 7)).toString(),
        periodType = "Weeks"
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
        ) to "Years"

        abs(duration.toHours()) > 24 -> duration.toDays() to "Days"
        abs(duration.toMinutes()) > 60 -> duration.toHours() to "Hours"
        else -> duration.toMinutes() to "Minutes"
    }
    return DateHandler(
        isInPast = duration.isNegative,
        value = abs(timePeriod.first).toString(),
        periodType = timePeriod.second
    )
}