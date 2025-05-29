package com.ulises.list.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.domain.models.YearsData
import com.ulises.components.toolbars.Toolbar
import com.ulises.components.toolbars.ToolbarItem
import com.ulises.list.models.Actions
import com.ulises.list.models.UiState
import com.ulises.list.ui.MainBottomSheetDialog
import com.ulises.list.ui.components.CountDownGridList
import com.ulises.list.ui.components.CountDownList
import com.ulises.list.ui.components.CurrentDayDataItem
import com.ulises.list.ui.components.LoadingComponent
import com.ulises.list.ui.components.YearListComponent
import com.ulises.preview_data.listItemsPreview
import com.ulises.theme.CountdownAppTheme

@Composable
internal fun CountdownMainScreen(
    uiState: UiState,
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
            onHandleAction(Actions.Interaction.DismissError)
        }
    }

    if (uiState.isSelectionMode) {
        BackHandler { onHandleAction(Actions.Interaction.CancelSelection) }
    }

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = com.ulises.common.resources.R.string.main_screen_title),
                actions = {
                    ToolbarItem(
                        imageVector = Icons.Filled.Add,
                        description = "Add",
                        onClick = { onHandleAction(Actions.Navigation.AddItem) },
                    )
                    ToolbarItem(
                        iconRes = if (!uiState.isGrid) com.ulises.common.resources.R.drawable.ic_grid_view else com.ulises.common.resources.R.drawable.ic_view_list,
                        description = "Change View",
                        isVisible = emptyValues,
                        onClick = { onHandleAction(Actions.Interaction.ToggleListType) }
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        floatingActionButton = {
            AnimatedVisibility(visible = uiState.isSelectionMode) {
                FloatingActionButton(onClick = { onHandleAction(Actions.Interaction.DeleteSelectedItems) }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            CurrentDayDataItem(onClickMoreData = { bottomSheetVisible = true })
            when {
                //  Loading
                uiState.loading -> {
                    LoadingComponent()
                }
                //  Empty State
                uiState.activeItems.isNullOrEmpty() && uiState.passedItems.isNullOrEmpty() -> {
                    NoEventsScreen()
                }
                //  Data
                else -> {
                    if (uiState.yearsData != null && uiState.yearsData.items.size > 1) {
                        YearListComponent(uiState.yearsData) {
                            onHandleAction(Actions.Interaction.ChangeSelectedYear(it))
                        }
                    }
                    if (!uiState.isGrid) {
                        CountDownList(
                            items = uiState.activeItems.orEmpty(),
                            passedItems = uiState.passedItems.orEmpty(),
                            selectedItems = uiState.selectedEvents,
                            isSelectionMode = uiState.isSelectionMode,
                            onClickItem = { event ->
                                if (uiState.isSelectionMode) {
                                    onHandleAction(Actions.Interaction.AddSelectedItem(event.id))
                                } else {
                                    onHandleAction(Actions.Navigation.DetailItem(event))
                                }
                            },
                            onLongClickItem = { event ->
                                onHandleAction(Actions.Interaction.AddSelectedItem(event.id))
                            },
                            onCountdownClick = { event ->
                                onHandleAction(Actions.Interaction.ChangeTimeCalculation(event))
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
                                    onHandleAction(Actions.Interaction.AddSelectedItem(event.id))
                                } else {
                                    onHandleAction(Actions.Navigation.DetailItem(event))
                                }
                            },
                            onLongClickItem = { event ->
                                onHandleAction(Actions.Interaction.AddSelectedItem(event.id))
                            },
                            onCountdownClick = { event ->
                                onHandleAction(Actions.Interaction.ChangeTimeCalculation(event))
                            },
                        )
                    }
                }
            }
        }
    }
    if (bottomSheetVisible) {
        MainBottomSheetDialog(onDismiss = { bottomSheetVisible = false })
    }
}

@PreviewLightDark
@Composable
private fun PrevCountDownScreen() {
    CountdownAppTheme {
        CountdownMainScreen(
            uiState = UiState(
                loading = false,
                activeItems = listItemsPreview,
                yearsData = YearsData(
                    items = listOf("100", "200", "300"),
                    selected = "200"
                )
            ),
        )
    }
}

@PreviewLightDark
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

@PreviewLightDark
@Composable
private fun PrevCountDownScreenNoEvents() {
    CountdownAppTheme {
        CountdownMainScreen(
            uiState = UiState(
                loading = false,
                activeItems = null,
                passedItems = null,
            ),
        )
    }
}