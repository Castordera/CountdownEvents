package com.example.domain.models

data class DateTimeHandler(
    val isInPast: Boolean,
    val years: Long,
    val months: Long,
    val days: Long,
    val hours: Long,
    val minutes: Long,
    val seconds: Long
)

data class DateHandler(
    val isInPast: Boolean,
    val value: String,
    val periodType: String
)
