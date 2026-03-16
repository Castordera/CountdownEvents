package com.ulises.event_detail

sealed interface Action {
    data object BackPressed : Action
}