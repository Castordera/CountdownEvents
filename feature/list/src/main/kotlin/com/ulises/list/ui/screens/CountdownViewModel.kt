package com.ulises.list.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.enums.DateDisplayType
import com.example.domain.models.CountdownDate
import com.ulises.data.DataStorePreferences
import com.ulises.datastore.KEY_STORED_VALUES
import com.ulises.list.models.Actions
import com.ulises.list.models.UiState
import com.ulises.usecase.countdown.DeleteEventUseCase
import com.ulises.usecase.countdown.EditEventUseCase
import com.ulises.usecase.countdown.GetAllEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CountdownViewModel @Inject constructor(
    getAllEventsUseCase: GetAllEventsUseCase,
    private val dataStore: DataStorePreferences<Boolean>,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val editEventUseCase: EditEventUseCase,
) : ViewModel() {

    private val localState = MutableStateFlow(LocalState())

    private data class LocalState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val selectedEvents: Set<String> = emptySet(),
    )

    val uiState = combine(
        getAllEventsUseCase(),
        dataStore.get(KEY_STORED_VALUES),
        localState
    ) { events, isGrid, localState ->
        val items = events.handleEvents()
        UiState(
            loading = localState.isLoading,
            activeItems = items[true],
            passedItems = items[false],
            error = localState.error,
            isGrid = isGrid,
            isSelectionMode = localState.selectedEvents.isNotEmpty(),
            selectedEvents = localState.selectedEvents,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState()
    )

    fun onHandleAction(action: Actions) {
        when (action) {
            Actions.ToggleListType -> onListChangeAdapter()
            Actions.DismissError -> onErrorMessageDisplayed()
            is Actions.ChangeTimeCalculation -> onCountdownClickTypeChange(action.item)
            Actions.CancelSelection -> onCancelSelection()
            is Actions.AddSelectedItem -> onSelectEvent(action.item)
            Actions.DeleteSelectedItems -> onDeleteEvents()
        }
    }

    private fun List<CountdownDate>.handleEvents(): Map<Boolean, List<CountdownDate>> {
        val currentDay = LocalDateTime.now().minusDays(1)
        return this.groupBy { it.dateToCountdown > currentDay }
    }

    private fun onSelectEvent(eventId: String) {
        if (!localState.value.selectedEvents.contains(eventId)) {
            localState.update { it.copy(selectedEvents = it.selectedEvents.plus(eventId)) }
        } else {
            localState.update { it.copy(selectedEvents = it.selectedEvents.minus(eventId)) }
        }
    }

    private fun onDeleteEvents() {
        viewModelScope.launch {
            runCatching {
                deleteEventUseCase(localState.value.selectedEvents)
            }.onSuccess {
                localState.update { it.copy(selectedEvents = emptySet()) }
            }.onFailure {
                Timber.e(it, "Error deleting events")
            }
        }
    }

    private fun onListChangeAdapter() {
        viewModelScope.launch {
            runCatching {
                dataStore.save(KEY_STORED_VALUES, !uiState.value.isGrid)
            }.onFailure {
                Timber.e(it, "Error updating view type")
            }
        }
    }

    private fun onCountdownClickTypeChange(countdownDate: CountdownDate) {
        viewModelScope.launch {
            val dateType = when (countdownDate.dateDisplayType) {
                DateDisplayType.REGULAR -> DateDisplayType.WEEKLY
                DateDisplayType.WEEKLY -> DateDisplayType.REGULAR
            }
            runCatching {
                editEventUseCase(countdownDate.copy(dateDisplayType = dateType))
            }.onSuccess {
                Timber.d("Date type changed to $dateType")
            }.onFailure {
                Timber.e("Error changing data display type from: $countdownDate")
            }
        }
    }

    private fun onErrorMessageDisplayed() {
        viewModelScope.launch {
            localState.update { it.copy(error = null) }
        }
    }

    private fun onCancelSelection() {
        localState.update { it.copy(selectedEvents = emptySet()) }
    }
}
