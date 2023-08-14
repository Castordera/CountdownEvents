package com.example.countdownapp.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countdownapp.di.HumanReadableFormat
import com.example.domain.models.CountdownDate
import com.ulises.usecase.countdown.AddCountdownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    @HumanReadableFormat val humanReadableFormat: DateTimeFormatter,
    private val addCountdownUseCase: AddCountdownUseCase
) : ViewModel() {

    private lateinit var dateSelected: LocalDateTime
    private val _uiState = MutableStateFlow(AddUiState())
    val uiState = _uiState.asStateFlow()

    init {
        updateLocalDate(
            LocalDateTime.now()
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
        )
    }

    private fun updateLocalDate(date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.Default) {
            dateSelected = date
            Timber.d("Current date: $date")
            _uiState.update { it.copy(date = date.format(humanReadableFormat)) }
        }
    }

    fun onDatePicked(year: Int, month: Int, day: Int) {
        updateLocalDate(LocalDateTime.of(year, month + 1, day, 0, 0, 0))
    }

    fun onEventNameChanged(value: String) {
        _uiState.update { it.copy(eventName = value) }
    }

    fun onSaveEvent() {
        if (_uiState.value.eventName.isBlank()) return
        viewModelScope.launch {
            runCatching {
                val event = CountdownDate(
                    id = "${Date().time}",
                    name = _uiState.value.eventName,
                    createdAt = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                    dateToCountdown = dateSelected
                )
                addCountdownUseCase(event)
            }.fold(
                onSuccess = {
                    Timber.d("Event stored: $it")
                    _uiState.update { state -> state.copy(goBack = true) }
                },
                onFailure = {
                    Timber.e(it, "Error adding event")
                }
            )
        }
    }

    fun demo(visible: Boolean) {
        _uiState.update { it.copy(dateDialogVisible = visible) }
    }
}