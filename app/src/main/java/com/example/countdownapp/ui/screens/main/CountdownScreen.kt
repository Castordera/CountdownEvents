package com.example.countdownapp.ui.screens.main

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countdownapp.ui.common.TopBarItem
import com.example.countdownapp.ui.components.CountDownItem
import com.example.countdownapp.ui.components.Toolbar
import com.example.countdownapp.ui.theme.CountdownAppTheme
import com.example.domain.models.CountdownDate

@Composable
fun CountDownRoute(
    toolbarActions: List<TopBarItem> = emptyList(),
    viewModel: CountdownViewModel = viewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CountdownMainScreen(
        items = uiState.countdownItems,
        toolbarActions = toolbarActions,
        onNavigateToDetail = { onNavigateToDetail(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CountdownMainScreen(
    items: List<CountdownDate>?,
    toolbarActions: List<TopBarItem>,
    onNavigateToDetail: (String) -> Unit
) {
    Scaffold(
        topBar = { Toolbar(
            title = "Events",
            actions = toolbarActions
        ) }
    ) { padding ->
        if (items.isNullOrEmpty()) {
            return@Scaffold
        }
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(
                items = items,
                key = { "${it.name}_${it.id}" }
            ) {
                CountDownItem(
                    modifier = Modifier.animateItemPlacement(),
                    item = it,
                    onLongClick = {},
                    onClick = { onNavigateToDetail(it.name) }
                )
                Divider(color = Color.LightGray)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevCountDownScreen() {
    CountdownAppTheme {
        CountdownMainScreen(
            items = listOf(
                CountdownDate(
                    name = "Bebecita",
                    dateToCountdown = "2023-05-29T23:25:14.697982"
                ),
                CountdownDate(
                    name = "Birth Day",
                    dateToCountdown = "2023-12-08T23:25:14.697982"
                )
            ),
            toolbarActions = emptyList(),
            onNavigateToDetail = {}
        )
    }
}