package com.ulises.date_utils.calculation

import com.example.domain.models.TimeCalculationResponse
import java.time.LocalDate
import java.time.Month
import javax.inject.Inject

class YearCalculation @Inject constructor(): TimeCalculation {

    override fun calculate(today: LocalDate): TimeCalculationResponse {
        val currentDay = today.dayOfYear
        val yearLastDay = LocalDate.of(today.year, Month.DECEMBER, Month.DECEMBER.maxLength()).dayOfYear
        val remainingDays = yearLastDay - currentDay
        return TimeCalculationResponse.Year(currentDay = currentDay, daysLeft = remainingDays)
    }
}