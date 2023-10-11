package com.ulises.list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.enums.CountdownSortType
import com.example.domain.models.CountdownDate
import com.ulises.data.DataStorePreferences
import com.ulises.usecase.countdown.DeleteCountdownUseCase
import com.ulises.usecase.countdown.GetAllCountdownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CountdownViewModel @Inject constructor(
//    Todo("Extract this into another module probably create a use case for it")
//    @DataStoreListViewType
    private val dataStore: DataStorePreferences<Boolean>,
    private val getAllCountdownUseCase: GetAllCountdownUseCase,
    private val deleteCountdownUseCase: DeleteCountdownUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()
    //
    private var deleteItemId: String? = null

    init {
        onLoadData()
    }

    private fun onLoadData() {
        viewModelScope.launch {
            getAllCountdownUseCase()
                .onStart { _uiState.update { it.copy(loading = true) } }
                .transform { emit(sortList(it)) }
                .catch { error ->
                    Timber.e(error)
                    _uiState.update { it.copy(loading = false) }
                }
                .collect { items ->
                    Timber.d("Items stored: $items")
                    _uiState.update { it.copy(loading = false, countdownItems = items) }
                }
        }
        viewModelScope.launch {
            dataStore.getValue()
                .catch { Timber.e(it, "Error getting data store value") }
                .collect { value -> _uiState.update { it.copy(isGrid = value) } }
        }
    }

    private fun sortList(list: List<CountdownDate>): List<CountdownDate> {
        Timber.d("$list")
        return if (_uiState.value.sortType == CountdownSortType.DATE) {
            list.sortedBy { it.dateToCountdown }
        } else {
            list.sortedBy { it.id }
        }
    }

    fun onChangeSortType(option: CountdownSortType) {
        Timber.d("Sort type: $option")
        val list = _uiState.value.countdownItems
        if (list != null) {
            _uiState.update { it.copy(sortType = option, countdownItems = sortList(list)) }
        }
    }

    fun onListChangeAdapter() {
        viewModelScope.launch {
            runCatching {
                dataStore.save(!_uiState.value.isGrid)
            }.onFailure { Timber.e(it, "Error updating view type") }
        }
    }

    fun onChangeDialogVisibility(visible: Boolean) {
        if (!visible) deleteItemId = null
        _uiState.update { it.copy(dialogDeleteVisible = visible) }
    }

    fun onRequestDeleteItem(item: CountdownDate) {
        deleteItemId = item.id
        onChangeDialogVisibility(true)
    }

    fun onChangeCountdownItemDisplayType(countdownDate: CountdownDate) {
        viewModelScope.launch {

        }
    }

    fun onDeleteCountdownItem() {
        viewModelScope.launch {
            runCatching {
                checkNotNull(deleteItemId)
                deleteCountdownUseCase(deleteItemId!!)
            }.onFailure {
                Timber.e(it, "Error deleting countdown")
            }.onSuccess {
                onChangeDialogVisibility(false)
            }
        }
    }
}
