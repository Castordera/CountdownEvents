package com.ulises.addevent.model

sealed interface Actions {
    sealed interface Navigation : Actions {
        data object BackPressed : Navigation
    }

    sealed interface Interaction : Actions {
        data object DismissError : Interaction
        data object SendData : Interaction
        data class UpdateName(val name: String) : Interaction
        data class DateSelection(val time: Long?) : Interaction
    }
}