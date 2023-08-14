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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.countdownapp.R
import com.example.countdownapp.ui.common.TopBarItem
import com.example.countdownapp.ui.components.CountDownItemGrid
import com.example.countdownapp.ui.components.CountDownItemList
import com.example.countdownapp.ui.components.Toolbar
import com.example.countdownapp.ui.screens.utils.listItemsPreview
import com.example.countdownapp.ui.theme.CountdownAppTheme
import com.example.domain.models.CountdownDate

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
                title = "Events",
                actions = toolbarActions + listOf(
                    TopBarItem(
                        "",
                        if (!uiState.isGrid) R.drawable.ic_grid_view else R.drawable.ic_view_list,
                        viewModel::onListChangeAdapter
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
            CountdownMainScreen(
                modifier = Modifier.padding(padding),
                items = uiState.countdownItems!!,
                onNavigateToDetail = onNavigateToDetail,
                onDeleteItem = viewModel::onDeleteCountdownItem,
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