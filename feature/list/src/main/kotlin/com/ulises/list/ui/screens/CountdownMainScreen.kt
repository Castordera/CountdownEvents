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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.domain.models.CountdownDate
import com.ulises.components.Loading
import com.ulises.components.toolbars.Toolbar
import com.ulises.components.toolbars.ToolbarItem
import com.ulises.list.models.Actions
import com.ulises.list.models.UiState
import com.ulises.list.ui.MainBottomSheetDialog
import com.ulises.list.ui.components.CountDownGridList
import com.ulises.list.ui.components.CountDownList
import com.ulises.list.ui.components.CurrentDayDataItem
import com.ulises.preview_data.listItemsPreview
import com.ulises.theme.CountdownAppTheme

@Composable
internal fun CountdownMainScreen(
    uiState: UiState,
    onAddNewClick: () -> Unit = {},
    onClickItem: (CountdownDate) -> Unit = {},
    onHandleAction: (Actions) -> Unit = {},
) {
    var bottomSheetVisible by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val emptyValues by remember(uiState) {
        derivedStateOf { !uiState.activeItems.isNullOrEmpty() || !uiState.passedItems.isNullOrEmpty() }
    }

    if (uiState.error != null) {
        LaunchedEffect(uiState.error) {
            snackBarHostState.showSnackbar(uiState.error)
            onHandleAction(Actions.DismissError)
        }
    }

    if (uiState.isSelectionMode) {
        BackHandler { onHandleAction(Actions.CancelSelection) }
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
                        isVisible = emptyValues,
                        onClick = { onHandleAction(Actions.ToggleListType) }
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        floatingActionButton = {
            AnimatedVisibility(visible = uiState.isSelectionMode) {
                FloatingActionButton(onClick = { onHandleAction(Actions.DeleteSelectedItems) }) {
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
            Column(
                modifier = Modifier.padding(padding)
            ) {
                CurrentDayDataItem(onClickMoreData = { bottomSheetVisible = true })
                if (!uiState.isGrid) {
                    CountDownList(
                        items = uiState.activeItems.orEmpty(),
                        passedItems = uiState.passedItems.orEmpty(),
                        selectedItems = uiState.selectedEvents,
                        isSelectionMode = uiState.isSelectionMode,
                        onClickItem = { event ->
                            if (uiState.isSelectionMode) {
                                onHandleAction(Actions.AddSelectedItem(event.id))
                            } else {
                                onClickItem(event)
                            }
                        },
                        onLongClickItem = { event ->
                            onHandleAction(Actions.AddSelectedItem(event.id))
                        },
                        onCountdownClick = { event ->
                            onHandleAction(Actions.ChangeTimeCalculation(event))
                        },
                    )
                } else {
                    CountDownGridList(
                        items = uiState.activeItems.orEmpty(),
                        passedItems = uiState.passedItems.orEmpty(),
                        selectedItems = uiState.selectedEvents,
                        isSelectionMode = uiState.isSelectionMode,
                        onClickItem = { event ->
                            if (uiState.isSelectionMode) {
                                onHandleAction(Actions.AddSelectedItem(event.id))
                            } else {
                                onClickItem(event)
                            }
                        },
                        onLongClickItem = { event ->
                            onHandleAction(Actions.AddSelectedItem(event.id))
                        },
                        onCountdownClick = { event ->
                            onHandleAction(Actions.ChangeTimeCalculation(event))
                        },
                    )
                }
            }
        }
        if (bottomSheetVisible) {
            MainBottomSheetDialog(onDismiss = { bottomSheetVisible = false })
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