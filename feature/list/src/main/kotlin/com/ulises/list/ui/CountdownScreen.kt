@file:OptIn(ExperimentalFoundationApi::class)

package com.ulises.list.ui

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.CountdownDate
import com.ulises.components.dialogs.SimpleAlertDialog
import com.ulises.components.toolbars.Toolbar
import com.ulises.components.toolbars.TopBarItem
import com.ulises.list.R
import com.example.domain.enums.CountdownSortType
import com.ulises.list.models.UiState
import com.ulises.preview_data.listItemsPreview
import com.ulises.theme.CountdownAppTheme

@Composable
fun CountDownRoute(
    toolbarActions: List<TopBarItem> = emptyList(),
    viewModel: CountdownViewModel = hiltViewModel(),
    onNavigateToDetail: (CountdownDate) -> Unit
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
        toolbarActions = toolbarActions,
        onSortTypeChange = viewModel::onChangeSortType,
        onListTypeChange = viewModel::onListChangeAdapter,
        onClickItem = onNavigateToDetail,
        onDeleteItem = viewModel::onRequestDeleteItem,
        onCountdownClickTypeChange = viewModel::onCountdownClickTypeChange,
        onErrorDisplayed = viewModel::onErrorMessageDisplayed,
    )
}

@Composable
private fun CountdownMainScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    toolbarActions: List<TopBarItem> = emptyList(),
    onSortTypeChange: (CountdownSortType) -> Unit,
    onListTypeChange: () -> Unit,
    onClickItem: (CountdownDate) -> Unit,
    onDeleteItem: (CountdownDate) -> Unit,
    onCountdownClickTypeChange: (CountdownDate) -> Unit,
    onErrorDisplayed: () -> Unit,
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
                actions = toolbarActions + listOf(
                    TopBarItem(
                        description = "Sort By",
                        icon = R.drawable.ic_sort,
                        onClick = { bottomSheetVisible = true },
                        isVisible = !uiState.countdownItems.isNullOrEmpty()
                    ),
                    TopBarItem(
                        description = "Change View",
                        icon = if (!uiState.isGrid) R.drawable.ic_grid_view else R.drawable.ic_view_list,
                        onClick = onListTypeChange,
                        isVisible = !uiState.countdownItems.isNullOrEmpty()
                    )
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState)}
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
                    onClickItem = onClickItem,
                    onDeleteItem = onDeleteItem,
                    onCountdownClick = onCountdownClickTypeChange
                )
            } else {
                CountDownGridList(
                    modifier = modifier.padding(padding),
                    items = uiState.countdownItems,
                    onClickItem = onClickItem,
                    onDeleteItem = onDeleteItem
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

@Composable
private fun CountDownList(
    modifier: Modifier = Modifier,
    items: List<CountdownDate>,
    onClickItem: (CountdownDate) -> Unit,
    onDeleteItem: (CountdownDate) -> Unit,
    onCountdownClick: (CountdownDate) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier
    ) {
        item {
            CurrentDayDataItem()
        }
        items(
            items = items,
            key = { "${it.name}_${it.id}" }
        ) {
            CountDownItemList(
                modifier = Modifier.animateItemPlacement(),
                item = it,
                onClick = onClickItem,
                onDelete = onDeleteItem,
                onCountdownClick = onCountdownClick,
            )
        }
    }
}

@Composable
private fun CountDownGridList(
    modifier: Modifier = Modifier,
    columns: Int = 2,
    items: List<CountdownDate>,
    onClickItem: (CountdownDate) -> Unit,
    onDeleteItem: (CountdownDate) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        modifier = modifier
    ) {
        item(
            span = { GridItemSpan(columns) }
        ) {
            CurrentDayDataItem()
        }
        items(
            items = items,
            key = { "${it.name}_${it.id}" }
        ) {
            CountDownItemGrid(
                item = it,
                onClick = onClickItem,
                onDelete = onDeleteItem,
                onCountdownClick = {},
            )
        }
    }
}

@Preview
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevCountDownScreen() {
    CountdownAppTheme {
        CountdownMainScreen(
            uiState = UiState(
                loading = false,
                countdownItems = listItemsPreview
            ),
            onClickItem = {},
            onDeleteItem = {},
            onListTypeChange = {},
            onSortTypeChange = {},
            onCountdownClickTypeChange = {},
            onErrorDisplayed = {},
        )
    }
}

@Preview
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevCountDownScreenGrid() {
    CountdownAppTheme {
        CountdownMainScreen(
            uiState = UiState(
                loading = false,
                countdownItems = listItemsPreview,
                isGrid = true
            ),
            onClickItem = {},
            onDeleteItem = {},
            onListTypeChange = {},
            onSortTypeChange = {},
            onCountdownClickTypeChange = {},
            onErrorDisplayed = {},
        )
    }
}

@Preview
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevCountDownScreenNoEvents() {
    CountdownAppTheme {
        CountdownMainScreen(
            uiState = UiState(),
            onClickItem = {},
            onDeleteItem = {},
            onListTypeChange = {},
            onSortTypeChange = {},
            onCountdownClickTypeChange = {},
            onErrorDisplayed = {},
        )
    }
}