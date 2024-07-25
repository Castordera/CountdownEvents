package com.ulises.list.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.CountdownDate
import com.ulises.list.ui.screens.CountdownMainScreen
import com.ulises.list.ui.screens.CountdownViewModel

@Composable
fun CountDownRoute(
    viewModel: CountdownViewModel = hiltViewModel(),
    onNavigateToDetail: (CountdownDate) -> Unit,
    onNavigateToAdd: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CountdownMainScreen(
        uiState = uiState,
        onAddNewClick = onNavigateToAdd,
        onClickItem = onNavigateToDetail,
        onHandleAction = viewModel::onHandleAction,
    )
}