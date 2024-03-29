package com.ulises.date_utils

import java.time.Instant
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

fun LocalDateTime?.toHumanReadable(): String {
    if (this == null) return ""
    val format = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return format(format)
}