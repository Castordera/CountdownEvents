package com.example.domain.models

import com.example.domain.enums.Seasons

sealed interface TimeCalculationResponse {
    data class Today(val date: String): TimeCalculationResponse
    data class Week(val current: Int, val left: Int): TimeCalculationResponse
    data class Year(val currentDay: Int, val daysLeft: Int): TimeCalculationResponse
    data class Month(val currentDay: Int, val daysLeft: Int): TimeCalculationResponse
    data class Season(val current: Seasons, val daysLeft: Int): TimeCalculationResponse
}