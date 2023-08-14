package com.example.domain.models

import java.time.LocalDateTime

data class CountdownDate(
    val id: String,
    val name: String,
    val createdAt: String,
    val dateToCountdown: LocalDateTime
)