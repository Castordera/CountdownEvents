package com.ulises.date_utils.calculation

import com.example.domain.models.TimeCalculationResponse
import com.ulises.date_utils.YEAR_MAX_WEEKS
import java.time.LocalDate
import java.time.temporal.ChronoField
import javax.inject.Inject

class WeekCalculation @Inject constructor(): TimeCalculation {

    override fun calculate(today: LocalDate): TimeCalculationResponse {
        val currentWeek = today.get(ChronoField.ALIGNED_WEEK_OF_YEAR) % YEAR_MAX_WEEKS
        val remainingWeeks = (YEAR_MAX_WEEKS - currentWeek)
        return TimeCalculationResponse.Week(current = currentWeek, left = remainingWeeks)
    }
}