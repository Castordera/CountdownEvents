package com.ulises.event_detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.models.CountdownDate
import com.ulises.common.navigation.Screen
import com.ulises.date_utils.MAX_HOURS_DAY
import com.ulises.date_utils.MAX_MINUTES_HOUR
import com.ulises.event_detail.models.Actions
import com.ulises.event_detail.models.DayDetail
import com.ulises.event_detail.models.DetailUiState
import com.ulises.usecase.countdown.AddEventUseCase
import com.ulises.usecase.countdown.GetEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class CountdownDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getCountdownDate: GetEventUseCase,
    private val addEventUseCase: AddEventUseCase,
) : ViewModel() {

    private val params = savedStateHandle.toRoute<Screen.CountdownDetail>()
    val uiState = getCountdownDate(params.countdownId)
        .map {
            DetailUiState(
                countdownDate = it,
                dayDetail = it.dateToCountdown.handleTime(),
            )
        }.catch {
            Timber.d(it, "Error getting event with Id: ${params.countdownId}")
            emit(DetailUiState(error = it.localizedMessage))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailUiState()
        )

    fun onHandleAction(action: Actions.Interaction) {
        when (action) {
            Actions.Interaction.DuplicateEvent -> onDuplicateEvent()
        }
    }

    private fun onDuplicateEvent() {
        viewModelScope.launch {
            runCatching {
                val item = uiState.value.countdownDate?.let {
                    CountdownDate(
                        id = "${Date().time}",
                        name = it.name,
                        createdAt = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                        dateToCountdown = it.dateToCountdown.plusYears(1)
                    )
                } ?: throw Exception("Error")
                addEventUseCase(item)
            }.onSuccess {

            }.onFailure {

            }
        }
    }

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
}