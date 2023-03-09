package com.example.countdownapp.ui.common

import com.example.domain.models.CountdownDate

data class CountdownDateUi(
    val id: String,
    val title: String,
    val date: String,
    val detailCountdown: DetailCountdown
)

data class DetailCountdown(
    val readableDate: String,
    val periodRemaining: PeriodRemaining,
    val timeRemaining: Int
)

enum class PeriodRemaining {
    YEARS,
    DAYS,
    HOURS,
    MINUTES
}

fun CountdownDate.toUi() = CountdownDateUi(
    id = id,
    title = name,
    date = dateToCountdown,
    detailCountdown = DetailCountdown(
        readableDate = "",
        periodRemaining = PeriodRemaining.DAYS,
        timeRemaining = 0
    )
)
