package com.ulises.list.ui

import androidx.lifecycle.ViewModel
import com.example.domain.models.TimeCalculationResponse
import com.ulises.date_utils.calculation.MonthCalculation
import com.ulises.date_utils.calculation.SeasonCalculation
import com.ulises.date_utils.calculation.TodayCalculation
import com.ulises.date_utils.calculation.WeekCalculation
import com.ulises.date_utils.calculation.YearCalculation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel @Inject constructor(
    private val todayCalculation: TodayCalculation,
    private val weekCalculation: WeekCalculation,
    private val monthCalculation: MonthCalculation,
    private val yearCalculation: YearCalculation,
    private val seasonCalculation: SeasonCalculation,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTimeCalculations()
    }

    private fun getTimeCalculations() {
        _uiState.update {
            it.copy(
                times = listOf(
                    todayCalculation.calculate(),
                    weekCalculation.calculate(),
                    monthCalculation.calculate(),
                    yearCalculation.calculate(),
                    seasonCalculation.calculate()
                )
            )
        }
    }

    data class UiState(
        val times: List<TimeCalculationResponse>? = null,
    )
}