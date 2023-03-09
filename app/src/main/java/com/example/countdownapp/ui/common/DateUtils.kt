package com.example.countdownapp.ui.common

import com.example.domain.models.CountdownDate
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

val CountdownDate.toReadableDate: String
get() {
    return runCatching {
        val format = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        LocalDateTime.parse(this.dateToCountdown).format(format)
    }.fold(
        onSuccess = { it },
        onFailure = { "Error decoding $this" }
    )
}

val CountdownDate.remainingTime: String
get() {
    return runCatching {
        val today = LocalDateTime.now()
        val formatted = LocalDateTime.parse(this.dateToCountdown)
        val duration = Duration.between(today, formatted)
        when {
            duration.toDays() > 365 -> ChronoUnit.YEARS.between(today, formatted)
            duration.toHours() > 24 -> duration.toDays()
            duration.toMinutes() > 60 -> duration.toHours()
            else -> duration.toMinutes()
        }
    }.fold(
        onSuccess = { it.toString() },
        onFailure = { "Error decoding $this" }
    )
}

val CountdownDate.remainingPeriod: String
get() {
    return runCatching {
        val today = LocalDateTime.now()
        val formatted = LocalDateTime.parse(this.dateToCountdown)
        val duration = Duration.between(today, formatted)
        when {
            duration.toDays() > 365 -> "Years"
            duration.toHours() > 24 -> "Days"
            duration.toMinutes() > 60 -> "Hours"
            else -> "Minutes"
        }
    }.fold(
        onSuccess = { it },
        onFailure = { "Error decoding $this" }
    )
}