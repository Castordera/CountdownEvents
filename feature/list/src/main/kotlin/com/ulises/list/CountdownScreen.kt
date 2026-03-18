package com.ulises.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.CountdownDate
import com.ulises.list.ui.contents.EventListContent

@Composable
fun CountDownScreen(
    viewModel: CountdownViewModel = hiltViewModel(),
    onNavigateToDetail: (CountdownDate) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    EventListContent(
        uiState = uiState,
        onHandleAction = { action ->
            when (action) {
                is Action.GoToDetailScreen -> onNavigateToDetail(action.event)
                else -> viewModel.onHandleAction(action)
            }
        }
    )
}