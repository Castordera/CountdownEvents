package com.ulises.event_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.models.CountdownDate
import com.ulises.common.navigation.Screen
import com.ulises.event_detail.models.UiState
import com.ulises.usecase.countdown.AddEventUseCase
import com.ulises.usecase.countdown.DeleteEventUseCase
import com.ulises.usecase.countdown.GetEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CountdownDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getCountdownDate: GetEventUseCase,
    private val addEventUseCase: AddEventUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
) : ViewModel() {

    private val params = savedStateHandle.toRoute<Screen.CountdownDetail>()
    private val localState = MutableStateFlow(UiState())
    val uiState = combine(getCountdownDate(params.countdownId), localState) { event, local ->
        UiState(
            message = local.message,
            forceBack = local.forceBack,
            countdownDate = event,
        )
    }.catch {
        Timber.d(it, "Error getting event with Id: ${params.countdownId}")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState()
    )

    fun onHandleAction(action: Action) {
        when (action) {
            is Action.DeleteEvent -> onDeleteEvent(params.countdownId)
            else -> {}
        }
    }

    private fun onDeleteEvent(eventId: String) {
        viewModelScope.launch {
            runCatching {
                deleteEventUseCase(eventId)
            }.onFailure {
                Timber.e(it, "Error deleting the Event: $eventId")
            }.onSuccess {
                Timber.d("Event deleted")
                localState.update { it.copy(forceBack = true) }
            }
        }
    }
}