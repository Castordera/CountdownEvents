package com.ulises.addevent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val addCountdownUseCase: AddCountdownUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddUiState(dateTime = LocalDateTime.now().zero()))
    val uiState = _uiState.asStateFlow()

    private fun updateLocalDate(date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.Default) {
            Timber.d("New Selected date: $date")
            _uiState.update {
                it.copy(
                    dateTime = date,
                    dateDialogVisible = false
                )
            }
        }
    }

    fun onDateSelected(timeMs: Long?) {
        timeMs?.also { updateLocalDate(it.toLocalDateTime()) }
    }

    fun onEventNameChanged(value: String) {
        _uiState.update { it.copy(eventName = value, saveButtonEnabled = value.isNotBlank()) }
    }

    fun onSaveEvent() {
        if (_uiState.value.eventName.isBlank()) return
        viewModelScope.launch {
            runCatching {
                val event = CountdownDate(
                    id = "${Date().time}",
                    name = _uiState.value.eventName.trim(),
                    createdAt = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                    dateToCountdown = _uiState.value.dateTime
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

    fun onChangeCalendarVisibility(visible: Boolean) {
        _uiState.update { it.copy(dateDialogVisible = visible) }
    }
}