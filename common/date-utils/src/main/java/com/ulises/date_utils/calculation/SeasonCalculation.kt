package com.ulises.date_utils.calculation

import com.example.domain.enums.Seasons
import com.example.domain.models.TimeCalculationResponse
import com.ulises.date_utils.daysTo
import java.time.LocalDate
import java.time.Month
import javax.inject.Inject

class SeasonCalculation @Inject constructor() : TimeCalculation {

    override fun calculate(today: LocalDate): TimeCalculationResponse {
        val springSeason = LocalDate.of(today.year, Month.MARCH, 21)
        val summerSeason = LocalDate.of(today.year, Month.JUNE, 21)
        val fallSeason = LocalDate.of(today.year, Month.SEPTEMBER, 21)
        val winterSeason = LocalDate.of(today.year, Month.DECEMBER, 21)

        val (currentSeason, daysLeft) = when {
            (today.dayOfYear >= springSeason.dayOfYear && today.dayOfYear < summerSeason.dayOfYear) -> {
                Seasons.SPRING to today.daysTo(summerSeason)
            }

            (today.dayOfYear >= summerSeason.dayOfYear && today.dayOfYear < fallSeason.dayOfYear) -> {
                Seasons.SUMMER to today.daysTo(fallSeason)
            }

            (today.dayOfYear >= fallSeason.dayOfYear && today.dayOfYear < winterSeason.dayOfYear) -> {
                Seasons.FALL to today.daysTo(winterSeason)
            }

            else -> Seasons.WINTER to today.daysTo(springSeason.plusYears(1))
        }
        return TimeCalculationResponse.Season(current = currentSeason, daysLeft = daysLeft)
    }
}