package com.ulises.addevent.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulises.addevent.model.Actions

@Composable
fun AddEventRoute(
    viewModel: AddEventViewModel = hiltViewModel(),
    onBackPress: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.goBack) {
        LaunchedEffect(Unit) {
            onBackPress()
        }
    }

    AddEventScreen(
        uiState = { uiState },
        onGetTextFieldValue = viewModel::getTextFieldValue,
        onHandleAction = {
            when (it) {
                is Actions.Navigation.BackPressed -> onBackPress()
                is Actions.Interaction -> viewModel.onHandleAction(it)
            }
        }
    )
}