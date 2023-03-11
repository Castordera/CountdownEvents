package com.example.countdownapp.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countdownapp.di.HumanReadableFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    @HumanReadableFormat val humanReadableFormat: DateTimeFormatter
) : ViewModel() {

    private var dateSelected = ""
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
            dateSelected = date.format(DateTimeFormatter.ISO_DATE)
            _uiState.update { it.copy(date = date.format(humanReadableFormat)) }
        }
    }

    fun onDatePicked(year: Int, month: Int, day: Int) {
        updateLocalDate(LocalDateTime.of(year, month + 1, day, 0, 0,0))
    }

    fun onEventNameChanged(value: String) {
        _uiState.update { it.copy(eventName = value) }
    }

    fun onSaveEvent() {
        viewModelScope.launch {

        }
    }
}