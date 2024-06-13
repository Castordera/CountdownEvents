package com.ulises.date_utils

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
    if (this == null) return ""
    val format = if (includeDay) {
        DateTimeFormatter.ofPattern("EEE, dd MMM yyyy")
    } else {
        DateTimeFormatter.ofPattern("dd MMM yyyy")
    }
    return format(format)
}

fun LocalDate?.toHumanReadable(): String {
    if (this == null) return ""
    return format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
}

fun LocalDate.daysTo(date: LocalDate): Int {
    return Duration.between(this, date).toDays().toInt()
}