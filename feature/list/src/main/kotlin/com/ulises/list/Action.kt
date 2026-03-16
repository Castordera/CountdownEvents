package com.ulises.list

import com.example.domain.models.CountdownDate

sealed interface Action {
    data object DisplayAddEventSheet : Action
    data object DismissAddEventSheet : Action
    data class SelectedYearClicked(val selection: String) : Action
    data class GoToDetailScreen(val event: CountdownDate) : Action
    data class AddEvent(val event: CountdownDate) : Action
}