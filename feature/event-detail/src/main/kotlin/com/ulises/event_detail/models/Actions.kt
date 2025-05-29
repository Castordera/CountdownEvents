package com.ulises.event_detail.models

sealed interface Actions {
    sealed interface Navigation : Actions {
        data object BackPressed : Navigation
        data class EditItem(val id: String) : Navigation
    }

    sealed interface Interaction : Actions {
        data object DuplicateEvent: Interaction
    }
}