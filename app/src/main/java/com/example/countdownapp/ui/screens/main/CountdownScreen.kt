@file:OptIn(ExperimentalFoundationApi::class)

package com.example.countdownapp.ui.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.countdownapp.R
import com.ulises.components.toolbars.TopBarItem
import com.example.countdownapp.ui.components.CountDownItemGrid
import com.example.countdownapp.ui.components.CountDownItemList
import com.ulises.components.toolbars.Toolbar
import com.example.countdownapp.ui.screens.utils.listItemsPreview
import com.ulises.theme.CountdownAppTheme
import com.example.domain.models.CountdownDate
import com.ulises.components.dialogs.SimpleAlertDialog

@Composable
fun CountDownRoute(
    toolbarActions: List<TopBarItem> = emptyList(),
    viewModel: CountdownViewModel = hiltViewModel(),
    onNavigateToDetail: (CountdownDate) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.main_screen_title),
                actions = toolbarActions + listOf(
                    TopBarItem(
                        description = "Change View",
                        icon = if (!uiState.isGrid) R.drawable.ic_grid_view else R.drawable.ic_view_list,
                        onClick = viewModel::onListChangeAdapter,
                        isVisible = !uiState.countdownItems.isNullOrEmpty()
                    )
                )
            )
        }
    ) { padding ->
        if (uiState.countdownItems.isNullOrEmpty()) {
            NoEventsScreen(
                modifier = Modifier.padding(padding)
            )
        } else {
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
                modifier = Modifier.padding(padding),
                items = uiState.countdownItems!!,
                onNavigateToDetail = onNavigateToDetail,
                onDeleteItem = viewModel::onRequestDeleteItem,
                isGrid = uiState.isGrid
            )
        }
    }
}

@Composable
fun CountdownMainScreen(
    modifier: Modifier = Modifier,
    items: List<CountdownDate>,
    onNavigateToDetail: (CountdownDate) -> Unit,
    onDeleteItem: (String) -> Unit,
    isGrid: Boolean = false
) {
    if (!isGrid) {
        CountDownList(
            modifier = modifier,
            items = items,
            onNavigateToDetail = onNavigateToDetail,
            onDeleteItem = onDeleteItem
        )
    } else {
        CountDownGridList(
            modifier = modifier,
            items = items,
            onNavigateToDetail = onNavigateToDetail,
            onDeleteItem = onDeleteItem
        )
    }
}

@Composable
private fun CountDownList(
    modifier: Modifier = Modifier,
    items: List<CountdownDate>,
    onNavigateToDetail: (CountdownDate) -> Unit,
    onDeleteItem: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier
    ) {
        items(
            items = items,
            key = { "${it.name}_${it.id}" }
        ) {
            CountDownItemList(
                modifier = Modifier.animateItemPlacement(),
                item = it,
                onClick = onNavigateToDetail,
                onDelete = onDeleteItem
            )
        }
    }
}

@Composable
private fun CountDownGridList(
    modifier: Modifier = Modifier,
    items: List<CountdownDate>,
    onNavigateToDetail: (CountdownDate) -> Unit,
    onDeleteItem: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        modifier = modifier
    ) {
        items(
            items = items,
            key = { "${it.name}_${it.id}" }
        ) {
            CountDownItemGrid(
                item = it,
                onClick = onNavigateToDetail
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevCountDownScreen() {
    CountdownAppTheme {
        CountdownMainScreen(
            items = listItemsPreview,
            onNavigateToDetail = {},
            onDeleteItem = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevCountDownScreenGrid() {
    CountdownAppTheme {
        CountdownMainScreen(
            isGrid = true,
            items = listItemsPreview,
            onNavigateToDetail = {},
            onDeleteItem = {}
        )
    }
}