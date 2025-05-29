package com.ulises.event_detail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulises.event_detail.models.Actions

@Composable
fun CountdownDetailRoute(
    viewModel: CountdownDetailViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onEditItem: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CountDownDetailScreen(
        uiState = { uiState },
        onHandleAction = {
            when (it) {
                is Actions.Navigation.BackPressed -> onBackPress()
                is Actions.Navigation.EditItem -> onEditItem(it.id)
                is Actions.Interaction -> viewModel.onHandleAction(it)
            }
        }
    )
}