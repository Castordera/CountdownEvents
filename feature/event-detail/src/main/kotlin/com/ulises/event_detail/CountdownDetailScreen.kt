package com.ulises.event_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulises.event_detail.content.EventDetailContent

@Composable
fun CountdownDetailScreen(
    viewModel: CountdownDetailViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onEditItem: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    EventDetailContent(
        uiState = uiState,
        onHandleAction = { action ->
            when(action) {
                Action.BackPressed -> onBackPress()
                else -> viewModel.onHandleAction(action)
            }
        }
    )
}