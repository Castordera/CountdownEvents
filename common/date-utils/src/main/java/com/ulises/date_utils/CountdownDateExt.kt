package com.ulises.date_utils

import com.example.domain.models.DateHandler
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.abs

val DateHandler.getQtyPast: Int
    get() = if (isInPast) 1 else 0

private fun Long.shouldAddDayExtra(isInPast: Boolean): Int {
    val days = abs(this).toInt()
    return if (isInPast) days else days + 1
}

//  V2
fun LocalDate.fromToday(): Int {
    return ChronoUnit.DAYS.between(LocalDate.now(), this).toInt()
}