package com.ulises.date_utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant.ofEpochMilli(this).atZone(ZoneId.of("UTC")).toLocalDateTime()
}

fun LocalDateTime.toMillis(): Long {
    return this.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli()
}
fun LocalDateTime.zero(): LocalDateTime {
    return this.withHour(0)
        .withMinute(0)
        .withSecond(0)
}

fun LocalDateTime?.toHumanReadable(includeDay: Boolean = false): String {
    if (this == null) return "N/A"
    val format = if (includeDay) {
        DateTimeFormatter.ofPattern("EEE, dd MMM yyyy")
    } else {
        DateTimeFormatter.ofPattern("dd MMM yyyy")
    }
    return format(format)
}

fun LocalDate?.toHumanReadable(pattern: String = "dd MMMM yyyy"): String {
    if (this == null) return "N/A"
    return format(DateTimeFormatter.ofPattern(pattern))
}

fun LocalDate.daysTo(date: LocalDate): Int {
    //Period.between()
    return ChronoUnit.DAYS.between(this, date).toInt()
}