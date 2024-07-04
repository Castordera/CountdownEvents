package com.ulises.list.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.CountdownDate
import com.ulises.components.dialogs.SimpleAlertDialog
import com.ulises.list.ui.screens.CountdownViewModel
import com.ulises.list.ui.screens.CountdownMainScreen

@Composable
fun CountDownRoute(
    viewModel: CountdownViewModel = hiltViewModel(),
    onNavigateToDetail: (CountdownDate) -> Unit,
    onNavigateToAdd: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(androidx.compose.ui.platform.LocalLifecycleOwner.current)

    SimpleAlertDialog(
        isVisible = uiState.dialogDeleteVisible,
        title = stringResource(id = com.ulises.common.resources.R.string.dialog_delete_event_title),
        message = stringResource(id = com.ulises.common.resources.R.string.dialog_delete_event_message),
        positiveTextButton = stringResource(id = com.ulises.common.resources.R.string.dialog_delete_event_positive_text),
        positiveClickButton = { viewModel.onDeleteCountdownItem() },
        negativeTextButton = stringResource(id = com.ulises.common.resources.R.string.dialog_delete_event_negative_text),
        negativeClickButton = { viewModel.onChangeDialogVisibility(false) },
        onDismissDialog = { viewModel.onChangeDialogVisibility(false) }
    )
    CountdownMainScreen(
        uiState = uiState,
        onAddNewClick = onNavigateToAdd,
        onListTypeChange = viewModel::onListChangeAdapter,
        onClickItem = onNavigateToDetail,
        onLongClickItem = viewModel::onRequestDeleteItem,
        onCountdownClickTypeChange = viewModel::onCountdownClickTypeChange,
        onErrorDisplayed = viewModel::onErrorMessageDisplayed,
        onAddSelectedEvent = viewModel::onSelectEvent,
        onDeleteSelectedEvents = viewModel::onDeleteEvents,
        onCancelSelection = viewModel::onCancelSelection,
    )
}