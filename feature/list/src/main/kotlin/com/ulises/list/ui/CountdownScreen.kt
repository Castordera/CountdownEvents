package com.ulises.list.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.enums.CountdownSortType
import com.example.domain.models.CountdownDate
import com.ulises.components.dialogs.SimpleAlertDialog
import com.ulises.components.toolbars.Toolbar
import com.ulises.components.toolbars.ToolbarItem
import com.ulises.list.R
import com.ulises.list.models.UiState
import com.ulises.list.ui.screens.CountDownGridList
import com.ulises.list.ui.screens.CountDownList
import com.ulises.list.ui.screens.NoEventsScreen
import com.ulises.preview_data.listItemsPreview
import com.ulises.theme.CountdownAppTheme

@Composable
fun CountDownRoute(
    viewModel: CountdownViewModel = hiltViewModel(),
    onNavigateToDetail: (CountdownDate) -> Unit,
    onNavigateToAdd: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SimpleAlertDialog(
        isVisible = uiState.dialogDeleteVisible,
        title = stringResource(id = R.string.dialog_delete_event_title),
        message = stringResource(id = R.string.dialog_delete_event_message),
        positiveTextButton = stringResource(id = R.string.dialog_delete_event_positive_text),
        positiveClickButton = { viewModel.onDeleteCountdownItem() },
        negativeTextButton = stringResource(id = R.string.dialog_delete_event_negative_text),
        negativeClickButton = { viewModel.onChangeDialogVisibility(false) },
        onDismissDialog = { viewModel.onChangeDialogVisibility(false) }
    )
    CountdownMainScreen(
        uiState = uiState,
        onAddNewClick = onNavigateToAdd,
        onSortTypeChange = viewModel::onChangeSortType,
        onListTypeChange = viewModel::onListChangeAdapter,
        onClickItem = onNavigateToDetail,
        onLongClickItem = viewModel::onRequestDeleteItem,
        onCountdownClickTypeChange = viewModel::onCountdownClickTypeChange,
        onErrorDisplayed = viewModel::onErrorMessageDisplayed,
        onAddSelectedEvent = viewModel::onSelectEvent,
        onDeleteSelectedEvents = viewModel::onDeleteEvents,
    )
}

@Composable
private fun CountdownMainScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onAddNewClick: () -> Unit = {},
    onSortTypeChange: (CountdownSortType) -> Unit = {},
    onListTypeChange: () -> Unit = {},
    onClickItem: (CountdownDate) -> Unit = {},
    onLongClickItem: (CountdownDate) -> Unit = {},
    onCountdownClickTypeChange: (CountdownDate) -> Unit = {},
    onErrorDisplayed: () -> Unit = {},
    onAddSelectedEvent: (String) -> Unit = {},
    onDeleteSelectedEvents: () -> Unit = {},
) {
    var bottomSheetVisible by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    if (uiState.error != null) {
        LaunchedEffect(uiState.error) {
            snackBarHostState.showSnackbar(uiState.error)
            onErrorDisplayed()
        }
    }

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.main_screen_title),
                actions = {
                    ToolbarItem(
                        imageVector = Icons.Filled.Add,
                        description = "Add",
                        onClick = onAddNewClick,
                    )
                    ToolbarItem(
                        iconRes = R.drawable.ic_sort,
                        description = "Sort by",
                        isVisible = !uiState.countdownItems.isNullOrEmpty(),
                        onClick = { bottomSheetVisible = true }
                    )
                    ToolbarItem(
                        iconRes = if (!uiState.isGrid) R.drawable.ic_grid_view else R.drawable.ic_view_list,
                        description = "Change View",
                        isVisible = !uiState.countdownItems.isNullOrEmpty(),
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
        if (uiState.countdownItems.isNullOrEmpty()) {
            NoEventsScreen(
                modifier = Modifier.padding(padding)
            )
        } else {
            if (!uiState.isGrid) {
                CountDownList(
                    modifier = modifier.padding(padding),
                    items = uiState.countdownItems,
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
                    onCountdownClick = onCountdownClickTypeChange
                )
            } else {
                CountDownGridList(
                    modifier = modifier.padding(padding),
                    items = uiState.countdownItems,
                    onClickItem = onClickItem,
                    onDeleteItem = onLongClickItem
                )
            }
        }
        if (bottomSheetVisible) {
            MainBottomSheetDialog(
                radioOptions = listOf(CountdownSortType.NORMAL, CountdownSortType.DATE),
                onClickItem = onSortTypeChange,
                selected = uiState.sortType,
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
                countdownItems = listItemsPreview
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
                countdownItems = listItemsPreview,
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
            uiState = UiState(),
        )
    }
}