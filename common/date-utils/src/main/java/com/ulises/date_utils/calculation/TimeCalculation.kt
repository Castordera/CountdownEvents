package com.ulises.date_utils.calculation

import com.example.domain.models.TimeCalculationResponse
import java.time.LocalDate

interface TimeCalculation {
    fun calculate(today: LocalDate = LocalDate.now()): TimeCalculationResponse
}