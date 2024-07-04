package com.ulises.event_detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.date_utils.MAX_HOURS_DAY
import com.ulises.date_utils.MAX_MINUTES_HOUR
import com.ulises.event_detail.models.DayDetail
import com.ulises.event_detail.models.DetailUiState
import com.ulises.event_detail.navigation.CountdownDetailScreen
import com.ulises.usecase.countdown.GetEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.math.abs

//Todo(Make this to handle time)
@HiltViewModel
class CountdownDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getCountdownDate: GetEventUseCase
) : ViewModel() {

    private val countdownDate: String = savedStateHandle[CountdownDetailScreen.argumentKey] ?: ""

    val uiState = getCountdownDate(countdownDate)
//        .onEach { handleTime(it.dateToCountdown) }
        .map { DetailUiState(countdownDate = it, dayDetail = it.dateToCountdown.handleTime()) }
        .catch {
            Timber.e(it, "Error getting event with Id: $countdownDate")
            emit(DetailUiState(error = it.localizedMessage))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailUiState()
        )

    private fun LocalDateTime.handleTime(): DayDetail {
        val today = LocalDateTime.now()
        val isLeapYear = LocalDate.of(this.year, this.month, this.dayOfMonth).isLeapYear
        val daysOfYear = if (isLeapYear) 366 else 365
        val isInPast = today.isAfter(this)
        //
        val years = ChronoUnit.YEARS.between(today, this).toInt()
        val days = ChronoUnit.DAYS.between(today, this).toInt()
        val hours = ChronoUnit.HOURS.between(today, this).toInt()
        val minutes = ChronoUnit.MINUTES.between(today, this).toInt()
        //
        val details = DayDetail(
            years = abs(years),
            days = abs(days) % daysOfYear,
            hours = abs(hours) % MAX_HOURS_DAY,
            minutes = abs(minutes) % MAX_MINUTES_HOUR,
            isPast = isInPast,
        )
        Timber.d("===$this===")
        Timber.d("$details")
        return details
    }

    fun demo() {
        val string = "thisismystring"
        val string2 = "asdadasa"
        val map = string.groupingBy { it }.eachCount().toMutableMap()
        string2.forEach { item ->
            if (map.contains(item)) {
            }
        }
        map.any { it.value != 0 }

    }
}