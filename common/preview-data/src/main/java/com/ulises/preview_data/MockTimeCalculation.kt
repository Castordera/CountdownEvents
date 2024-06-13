package com.ulises.preview_data

import com.example.domain.enums.Seasons
import com.example.domain.models.TimeCalculationResponse

val MOCK_TIME_CALCULATION_LIST_RESPONSE = listOf(
    TimeCalculationResponse.Today("[Today's date]"),
    TimeCalculationResponse.Week(1, 1),
    TimeCalculationResponse.Year(1, 1),
    TimeCalculationResponse.Month(1, 1),
    TimeCalculationResponse.Season(Seasons.FALL, 1),
)