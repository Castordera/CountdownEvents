package com.ulises.addevent

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.CountdownDate
import com.ulises.date_utils.toLocalDateTime
import com.ulises.date_utils.zero
import com.ulises.usecase.countdown.AddCountdownUseCase
import com.ulises.usecase.countdown.EditEventUseCase
import com.ulises.usecase.countdown.GetCountdownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
    savedStateHandle: SavedStateHandle,
    private val addCountdownUseCase: AddCountdownUseCase,
    private val getCountdownUseCase: GetCountdownUseCase,
    private val editEventUseCase: EditEventUseCase
) : ViewModel() {

    // Todo("Replace it to have only one, not all in Screens")
    private val eventId: String? = savedStateHandle["item"]
    private var event: CountdownDate? = null
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        setInitialData()
    }

    private fun setInitialData() {
        if (!isNewEvent()) {
            getEventData(eventId!!)
        } else {
            _uiState.update { it.copy(dateTime = LocalDateTime.now().zero(), isLoading = false) }
        }
    }

    private fun isNewEvent(): Boolean {
        Timber.d("Initial value: $eventId")
        return eventId == "null"
    }

    private fun getEventData(eventId: String) {
        viewModelScope.launch {
            getCountdownUseCase(eventId)
                .catch { Timber.e(it, "Error getting event") }
                .collect { event ->
                    Timber.d("Event received: $event")
                    this@AddEventViewModel.event = event
                    _uiState.update {
                        it.copy(
                            eventName = event.name,
                            dateTime = event.dateToCountdown,
                            saveButtonEnabled = true,
                            isLoading = false
                        )
                    }
                }
        }
    }

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
        if (isNewEvent()) {
            createNewEvent()
        } else {
            editEvent()
        }
    }

    private fun createNewEvent() {
        viewModelScope.launch {
            runCatching {
                val event = CountdownDate(
                    id = "${Date().time}",
                    name = _uiState.value.eventName.trim(),
                    createdAt = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                    dateToCountdown = _uiState.value.dateTime!!
                )
                Timber.d("Create Event: $event")
                addCountdownUseCase(event)
            }.onSuccess {
                Timber.d("Event stored")
                _uiState.update { state -> state.copy(goBack = true) }
            }.onFailure {
                Timber.e(it, "Error adding event")
            }
        }
    }

    private fun editEvent() {
        viewModelScope.launch {
            runCatching {
                checkNotNull(event)
                val newEvent = event!!.copy(
                    name = _uiState.value.eventName.trim(),
                    dateToCountdown = _uiState.value.dateTime!!
                )
                editEventUseCase(newEvent)
            }.onFailure {
                Timber.e(it, "Error editing event")
            }.onSuccess {
                Timber.d("Event edited success")
                _uiState.update { state -> state.copy(goBack = true) }
            }
        }
    }

    fun onChangeCalendarVisibility(visible: Boolean) {
        _uiState.update { it.copy(dateDialogVisible = visible) }
    }
}