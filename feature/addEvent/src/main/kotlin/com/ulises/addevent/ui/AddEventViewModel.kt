package com.ulises.addevent.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.models.CountdownDate
import com.ulises.addevent.model.Actions
import com.ulises.addevent.model.UiState
import com.ulises.common.navigation.Screen
import com.ulises.date_utils.toLocalDateTime
import com.ulises.date_utils.zero
import com.ulises.usecase.countdown.AddEventUseCase
import com.ulises.usecase.countdown.EditEventUseCase
import com.ulises.usecase.countdown.GetEventUseCase
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
    private val addEventUseCase: AddEventUseCase,
    private val getEventUseCase: GetEventUseCase,
    private val editEventUseCase: EditEventUseCase
) : ViewModel() {

    val eventId: String? = savedStateHandle.toRoute<Screen.AddEditCountdown>().countdownId

    private var event: CountdownDate? = null
    private var name by mutableStateOf("")
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        setInitialData()
    }

    fun onHandleAction(action: Actions) {
        when (action) {
            Actions.DismissError -> onErrorMessageDisplayed()
            Actions.SendData -> onSaveEvent()
            is Actions.UpdateName -> onEventNameChanged(action.name)
            is Actions.DateSelection -> onDateSelected(action.time)
        }
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
        return eventId == null
    }

    private fun getEventData(eventId: String) {
        viewModelScope.launch {
            getEventUseCase(eventId)
                .catch {
                    Timber.e(it, "Error getting event")
                }
                .collect { event ->
                    Timber.d("Event received: $event")
                    this@AddEventViewModel.event = event
                    _uiState.update {
                        it.copy(
                            dateTime = event.dateToCountdown,
                            isLoading = false
                        )
                    }
                    name = event.name
                }
        }
    }

    private fun updateLocalDate(date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.Default) {
            Timber.d("New Selected date: $date")
            _uiState.update { it.copy(dateTime = date) }
        }
    }

    private fun onDateSelected(timeMs: Long?) {
        timeMs?.also { updateLocalDate(it.toLocalDateTime()) }
    }

    private fun onEventNameChanged(value: String) {
        name = value
    }

    fun getTextFieldValue(): String {
        return name
    }

    private fun onSaveEvent() {
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
                    name = name.trim(),
                    createdAt = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                    dateToCountdown = _uiState.value.dateTime!!
                )
                Timber.d("Create Event: $event")
                addEventUseCase(event)
            }.onSuccess {
                Timber.d("Event stored")
                _uiState.update { state -> state.copy(goBack = true) }
            }.onFailure { error ->
                Timber.e(error, "Error adding event")
                _uiState.update { it.copy(error = error.localizedMessage) }
            }
        }
    }

    private fun editEvent() {
        viewModelScope.launch {
            runCatching {
                checkNotNull(event)
                val newEvent = event!!.copy(
                    name = name.trim(),
                    dateToCountdown = _uiState.value.dateTime!!
                )
                editEventUseCase(newEvent)
            }.onFailure { error ->
                Timber.e(error, "Error editing event")
                _uiState.update { it.copy(error = error.localizedMessage) }
            }.onSuccess {
                Timber.d("Event edited success")
                _uiState.update { state -> state.copy(goBack = true) }
            }
        }
    }

    private fun onErrorMessageDisplayed() {
        _uiState.update { it.copy(error = null) }
    }
}