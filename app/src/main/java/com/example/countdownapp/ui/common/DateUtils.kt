package com.example.countdownapp.ui.common

import com.example.domain.models.CountdownDate
import com.example.domain.models.DateHandler
import com.example.domain.models.DateTimeHandler
import timber.log.Timber
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.abs

val CountdownDate.toReadableDate: String
    get() {
        return runCatching {
            val format = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            dateToCountdown.format(format)
        }.fold(
            onSuccess = { it },
            onFailure = { "Error decoding $this" }
        )
    }

val CountdownDate.timeHandler: DateTimeHandler?
    get() {
        return runCatching {
            val today = LocalDateTime.now()
            val formatted = dateToCountdown
            val duration = Duration.between(today, formatted)
            DateTimeHandler(
                isInPast = duration.isNegative,
                years = ChronoUnit.YEARS.between(today, formatted),
                months = ChronoUnit.MONTHS.between(today, formatted),
                days = duration.toDays(),
                hours = duration.toHours(),
                minutes = duration.toMinutes(),
                seconds = duration.seconds
            )
        }.getOrElse {
            Timber.e(it)
            null
        }
    }

val CountdownDate.remainingTime: DateHandler
    get() {
        return runCatching {
            val today = LocalDateTime.now()
            val formatted = dateToCountdown
            val duration = Duration.between(today, formatted)
            val timePeriod = when {
                abs(duration.toDays()) > 365 -> {
                    ChronoUnit.YEARS.between(today, formatted) to "Years"
                }
                abs(duration.toHours()) > 24 -> {
                    duration.toDays() to "Days"
                }
                abs(duration.toMinutes()) > 60 -> {
                    duration.toHours() to "Hours"
                }
                else -> {
                    duration.toMinutes() to "Minutes"
                }
            }
            DateHandler(
                isInPast = duration.isNegative,
                value = abs(timePeriod.first).toString(),
                periodType = timePeriod.second
            )
        }.getOrElse {
            Timber.e(it)
            DateHandler(false, "Error", "Error")
        }
    }