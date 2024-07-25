package com.ulises.addevent.model

sealed interface Actions {
    data object DismissError: Actions
    data object SendData: Actions
    data class UpdateName(val name: String): Actions
    data class DateSelection(val time: Long?): Actions
}