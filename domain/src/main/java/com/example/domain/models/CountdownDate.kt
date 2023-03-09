package com.example.domain.models

import java.util.*

data class CountdownDate(
    val id: String = "${Date().time}",
    val name: String,
    val dateToCountdown: String
)