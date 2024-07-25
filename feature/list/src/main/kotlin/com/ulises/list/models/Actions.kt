package com.ulises.list.models

import com.example.domain.models.CountdownDate

sealed interface Actions {
    data object ToggleListType: Actions
    data object DismissError: Actions
    data class ChangeTimeCalculation(val item: CountdownDate): Actions
    data class AddSelectedItem(val item: String): Actions
    data object DeleteSelectedItems: Actions
    data object CancelSelection: Actions
}