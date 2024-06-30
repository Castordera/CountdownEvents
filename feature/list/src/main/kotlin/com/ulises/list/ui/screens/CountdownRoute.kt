package com.ulises.list.ui.screens

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.CountdownDate
import com.ulises.components.Loading
import com.ulises.components.dialogs.SimpleAlertDialog
import com.ulises.components.toolbars.Toolbar
import com.ulises.components.toolbars.ToolbarItem
import com.ulises.list.models.UiState
import com.ulises.list.ui.CountdownViewModel
import com.ulises.list.ui.MainBottomSheetDialog
import com.ulises.preview_data.listItemsPreview
import com.ulises.theme.CountdownAppTheme

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

@Composable
private fun CountdownMainScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onAddNewClick: () -> Unit = {},
    onListTypeChange: () -> Unit = {},
    onClickItem: (CountdownDate) -> Unit = {},
    onLongClickItem: (CountdownDate) -> Unit = {},
    onCountdownClickTypeChange: (CountdownDate) -> Unit = {},
    onErrorDisplayed: () -> Unit = {},
    onAddSelectedEvent: (String) -> Unit = {},
    onDeleteSelectedEvents: () -> Unit = {},
    onCancelSelection: () -> Unit = {},
) {
    var bottomSheetVisible by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    if (uiState.error != null) {
        LaunchedEffect(uiState.error) {
            snackBarHostState.showSnackbar(uiState.error)
            onErrorDisplayed()
        }
    }

    if (uiState.isSelectionMode) {
        BackHandler {
            onCancelSelection()
        }
    }

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = com.ulises.common.resources.R.string.main_screen_title),
                actions = {
                    ToolbarItem(
                        imageVector = Icons.Filled.Add,
                        description = "Add",
                        onClick = onAddNewClick,
                    )
                    ToolbarItem(
                        iconRes = if (!uiState.isGrid) com.ulises.common.resources.R.drawable.ic_grid_view else com.ulises.common.resources.R.drawable.ic_view_list,
                        description = "Change View",
                        isVisible = !uiState.activeItems.isNullOrEmpty(),
                        onClick = onListTypeChange
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        floatingActionButton = {
            AnimatedVisibility(visible = uiState.isSelectionMode) {
                FloatingActionButton(onClick = onDeleteSelectedEvents) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                }
            }
        }
    ) { padding ->
        if (uiState.loading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Loading()
            }
        } else if (uiState.activeItems.isNullOrEmpty() && uiState.passedItems.isNullOrEmpty()) {
            NoEventsScreen(modifier = Modifier.padding(padding))
        } else {
            if (!uiState.isGrid) {
                CountDownList(
                    modifier = modifier.padding(padding),
                    items = uiState.activeItems.orEmpty(),
                    passedItems = uiState.passedItems.orEmpty(),
                    selectedItems = uiState.selectedEvents,
                    isSelectionMode = uiState.isSelectionMode,
                    onClickItem = { event ->
                        if (uiState.isSelectionMode) {
                            onAddSelectedEvent(event.id)
                        } else {
                            onClickItem(event)
                        }
                    },
                    onLongClickItem = { event -> onAddSelectedEvent(event.id) },
                    onCountdownClick = onCountdownClickTypeChange,
                    onClickMoreData = { bottomSheetVisible = true }
                )
            } else {
                CountDownGridList(
                    modifier = modifier.padding(padding),
                    items = uiState.activeItems.orEmpty(),
                    passedItems = uiState.passedItems.orEmpty(),
                    onClickItem = onClickItem,
                    onDeleteItem = onLongClickItem
                )
            }
        }
        if (bottomSheetVisible) {
            MainBottomSheetDialog(
                onDismiss = { bottomSheetVisible = false }
            )
        }
    }
}

@Preview
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrevCountDownScreen() {
    CountdownAppTheme {
        CountdownMainScreen(
            uiState = UiState(
                loading = false,
                activeItems = listItemsPreview
            ),
        )
    }
}

@Preview
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrevCountDownScreenGrid() {
    CountdownAppTheme {
        CountdownMainScreen(
            uiState = UiState(
                loading = false,
                activeItems = listItemsPreview,
                isGrid = true
            ),
        )
    }
}

@Preview
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrevCountDownScreenNoEvents() {
    CountdownAppTheme {
        CountdownMainScreen(
            uiState = UiState(loading = false),
        )
    }
}