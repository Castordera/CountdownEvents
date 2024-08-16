package com.ulises.list.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.enums.DateDisplayType
import com.example.domain.models.CountdownDate
import com.example.domain.models.YearsData
import com.ulises.data.DataStorePreferences
import com.ulises.datastore.KEY_STORED_VALUES
import com.ulises.list.models.Actions
import com.ulises.list.models.UiState
import com.ulises.usecase.countdown.DeleteEventUseCase
import com.ulises.usecase.countdown.EditEventUseCase
import com.ulises.usecase.countdown.GetAllEventsUseCase
import com.ulises.usecase.countdown.GetYearsWithDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CountdownViewModel @Inject constructor(
    getAllEventsUseCase: GetAllEventsUseCase,
    getYearsWithDataUseCase: GetYearsWithDataUseCase,
    private val dataStore: DataStorePreferences<Boolean>,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val editEventUseCase: EditEventUseCase,
) : ViewModel() {

    private val localState = MutableStateFlow(LocalState())
    private val selectedYearFlow = MutableStateFlow(LocalDate.now().year.toString())

    private data class LocalState(
        val error: String? = null,
        val selectedEvents: Set<String> = emptySet(),
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = combine(getYearsWithDataUseCase(), selectedYearFlow) { years, selectedYear ->
        Timber.d("Year Flow triggered")
        YearsData(years, if (years.size == 1) years[0] else selectedYear)
    }.flatMapLatest { yearsData ->
        //Todo(Manage years to reduce upcoming years into one item only e.g [Upcoming, 2024, 2023])
        Timber.d("Data passed down: $yearsData")
        Timber.d("Now request data for a selected year")
        combine(
            getAllEventsUseCase(yearsData.selected),
            localState,
            dataStore.get(KEY_STORED_VALUES)
        ) { events, localState, isGrid ->
            Timber.d("Combined Flow triggered $events")
            Timber.d("Combined Flow triggered $localState")
            Timber.d("Combined Flow triggered $isGrid")
            val items = events.handleEvents()
            UiState(
                loading = false,
                activeItems = items[true],
                passedItems = items[false],
                error = localState.error,
                isGrid = isGrid,
                isSelectionMode = localState.selectedEvents.isNotEmpty(),
                selectedEvents = localState.selectedEvents,
                yearsData = yearsData,
            )
        }
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
            is Actions.ChangeSelectedYear -> onChangeSelectedYear(action.year)
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
        viewModelScope.launch { localState.update { it.copy(error = null) } }
    }

    private fun onCancelSelection() {
        localState.update { it.copy(selectedEvents = emptySet()) }
    }

    private fun onChangeSelectedYear(selectedYear: String) {
        selectedYearFlow.update { selectedYear }
    }
}
