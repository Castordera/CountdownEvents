package com.ulises.list.models

import com.example.domain.models.CountdownDate

sealed interface Actions {
    sealed interface Interaction: Actions {
        data object ToggleListType : Interaction
        data object DismissError : Interaction
        data class ChangeTimeCalculation(val item: CountdownDate) : Interaction
        data class AddSelectedItem(val item: String) : Interaction
        data object DeleteSelectedItems : Interaction
        data object CancelSelection : Interaction
        data class ChangeSelectedYear(val year: String) : Interaction
    }
    sealed interface Navigation : Actions {
        data object AddItem : Navigation
        data class DetailItem(val item: CountdownDate) : Navigation
    }
}