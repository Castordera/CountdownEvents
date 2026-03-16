package com.example.domain.models

import com.example.domain.enums.DateDisplayType
import java.time.LocalDate
import java.time.LocalDateTime

data class CountdownDate(
    val id: String,
    val name: String,
    val createdAt: String,
    val date: LocalDate,
    val dateDisplayType: DateDisplayType = DateDisplayType.REGULAR,
    val emoji: String = "🐶",
)