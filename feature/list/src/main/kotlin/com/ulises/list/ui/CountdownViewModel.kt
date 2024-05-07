package com.ulises.list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.enums.CountdownSortType
import com.example.domain.enums.DateDisplayType
import com.example.domain.models.CountdownDate
import com.ulises.data.DataStorePreferences
import com.ulises.datastore.KEY_STORED_VALUES
import com.ulises.list.models.UiState
import com.ulises.usecase.countdown.DeleteEventUseCase
import com.ulises.usecase.countdown.EditEventUseCase
import com.ulises.usecase.countdown.GetAllEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CountdownViewModel @Inject constructor(
    getAllEventsUseCase: GetAllEventsUseCase,
    private val dataStore: DataStorePreferences<Boolean>,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val editEventUseCase: EditEventUseCase,
) : ViewModel() {

    private var deleteItemId: String? = null
    private val localState = MutableStateFlow(LocalState())

    private data class LocalState(
        val isLoading: Boolean = false,
        val isDialogDeleteVisible: Boolean = false,
        val error: String? = null,
        val selectedEvents: Set<String> = emptySet(),
    )

    val uiState = combine(
        getAllEventsUseCase(),
        dataStore.get(KEY_STORED_VALUES),
        localState
    ) { events, isGrid, localState ->
        UiState(
            loading = localState.isLoading,
            countdownItems = events,
            dialogDeleteVisible = localState.isDialogDeleteVisible,
            error = localState.error,
            isGrid = isGrid,
            isSelectionMode = localState.selectedEvents.isNotEmpty(),
            selectedEvents = localState.selectedEvents,
        )
    }.onEach {
        Timber.d("State change: ${localState.value}")
    }.onStart {
        Timber.d("Collector started")
    }.onCompletion {
        Timber.d("Collector ended")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState()
    )

//    private fun sortList(list: List<CountdownDate>): List<CountdownDate> {
//        Timber.d("$list")
//        return if (_uiState.value.sortType == CountdownSortType.DATE) {
//            list.sortedBy { it.dateToCountdown }
//        } else {
//            list.sortedBy { it.id }
//        }
//    }

    fun onChangeSortType(option: CountdownSortType) {
//        //Todo(Fix)
////        Timber.d("Sort type: $option")
////        val list = _uiState.value.countdownItems
////        if (list != null) {
////            _uiState.update { it.copy(sortType = option, countdownItems = sortList(list)) }
////        }
    }

    fun onSelectEvent(eventId: String) {
        if (!localState.value.selectedEvents.contains(eventId)) {
            localState.update { it.copy(selectedEvents = it.selectedEvents.plus(eventId)) }
        } else {
            localState.update { it.copy(selectedEvents = it.selectedEvents.minus(eventId)) }
        }
    }

    fun onDeleteEvents() {
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

    fun onListChangeAdapter() {
        viewModelScope.launch {
            runCatching {
                dataStore.save(KEY_STORED_VALUES, !uiState.value.isGrid)
            }.onFailure {
                Timber.e(it, "Error updating view type")
            }
        }
    }

    fun onChangeDialogVisibility(visible: Boolean) {
        if (!visible) deleteItemId = null
        localState.update { it.copy(isDialogDeleteVisible = visible) }
    }

    fun onRequestDeleteItem(item: CountdownDate) {
        deleteItemId = item.id
        onChangeDialogVisibility(true)
    }

    fun onDeleteCountdownItem() {
        viewModelScope.launch {
            runCatching {
                checkNotNull(deleteItemId)
                deleteEventUseCase(deleteItemId!!)
            }.onFailure {
                Timber.e(it, "Error deleting countdown")
            }.onSuccess {
                onChangeDialogVisibility(false)
            }
        }
    }

    fun onCountdownClickTypeChange(countdownDate: CountdownDate) {
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

    fun onErrorMessageDisplayed() {
        viewModelScope.launch {
            localState.update { it.copy(error = null) }
        }
    }

    fun onCancelSelection() {
        localState.update { it.copy(selectedEvents = emptySet()) }
    }
}
