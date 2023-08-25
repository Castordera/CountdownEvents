package com.example.domain.models

import com.example.domain.enums.DateDisplayType
import java.time.LocalDateTime

data class CountdownDate(
    val id: String,
    val name: String,
    val createdAt: String,
    val dateToCountdown: LocalDateTime,
    val dateDisplayType: DateDisplayType = DateDisplayType.REGULAR
)