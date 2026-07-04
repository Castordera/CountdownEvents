package com.ulises.event_detail.models

sealed interface Action {
    data object BackPressed : Action
    data object DeleteEvent : Action
}