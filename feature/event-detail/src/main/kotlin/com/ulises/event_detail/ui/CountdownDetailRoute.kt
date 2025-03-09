package com.ulises.event_detail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CountdownDetailRoute(
    viewModel: CountdownDetailViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onEditItem: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CountDownDetailScreen(
        uiState = { uiState },
        onEditItem = onEditItem,
        onBackPress = onBackPress,
    )
}