package com.ulises.date_utils.calculation

import com.example.domain.models.TimeCalculationResponse
import com.ulises.date_utils.toHumanReadable
import java.time.LocalDate
import javax.inject.Inject

class TodayCalculation @Inject constructor(): TimeCalculation {

    override fun calculate(today: LocalDate): TimeCalculationResponse {
        return TimeCalculationResponse.Today(today.toHumanReadable())
    }
}