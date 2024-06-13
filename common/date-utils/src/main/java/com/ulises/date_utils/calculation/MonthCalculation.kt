package com.ulises.date_utils.calculation

import com.example.domain.models.TimeCalculationResponse
import java.time.LocalDate
import javax.inject.Inject

class MonthCalculation @Inject constructor(): TimeCalculation {

    override fun calculate(today: LocalDate): TimeCalculationResponse {
        val currentDay = today.dayOfMonth
        val monthTotalDays = today.month.maxLength()
        val remainingDays = monthTotalDays - currentDay
        return TimeCalculationResponse.Month(currentDay = currentDay, daysLeft = remainingDays)
    }
}