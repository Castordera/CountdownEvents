package com.example.domain.models

import java.time.LocalDateTime
import java.util.Date

data class CountdownDate(
    val id: String = "${Date().time}",
    val name: String,
    val createdAt: String,
    val dateToCountdown: LocalDateTime
)