package com.ulises.list.ui.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.domain.models.YearsData
import com.ulises.addevent.ui.AddEventSheet
import com.ulises.list.Action
import com.ulises.list.models.UiState
import com.ulises.list.ui.components.EventCard
import com.ulises.list.ui.components.ListHeader
import com.ulises.list.ui.components.ListTabs
import com.ulises.list.ui.components.SectionLabel
import com.ulises.list.ui.components.SwipeableCard
import com.ulises.preview_data.listItemsPreview
import com.ulises.theme.CountdownAppTheme

@Composable
internal fun EventListContent(
    uiState: UiState,
    onHandleAction: (Action) -> Unit = {},
) {
    val snackbarHostState = remember { SnackbarHostState() }

    if (uiState.addSheetVisible) {
        AddEventSheet(
            onSaveEvent = { onHandleAction(Action.AddEvent(it)) },
            onDismiss = { onHandleAction(Action.DismissAddEventSheet) }
        )
    }
    if (uiState.message.isNotEmpty()) {
        LaunchedEffect(uiState.message) {
            snackbarHostState.showSnackbar(
                message = uiState.message,
                withDismissAction = true,
            )
            onHandleAction(Action.SnackBarDismissed)
        }
    }
    //
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.onSurface,
                    contentColor = MaterialTheme.colorScheme.surface,
                    actionColor = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.medium,
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            ListHeader { onHandleAction(Action.DisplayAddEventSheet) }
            if (uiState.yearsData != null) {
                ListTabs(uiState.yearsData) {
                    onHandleAction(Action.SelectedYearClicked(it))
                }
            }
            if (uiState.activeItems.isEmpty() && uiState.passedItems.isEmpty()) {
                NoEventsScreen(modifier = Modifier.padding(innerPadding))
                return@Scaffold
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (uiState.activeItems.isNotEmpty()) {
                    item { SectionLabel("Proximos") }
                    items(uiState.activeItems, key = { it.id }) { event ->
                        SwipeableCard(
                            onDeleteEvent = { onHandleAction(Action.DeleteEvent(event)) }
                        ) {
                            EventCard(event) { onHandleAction(Action.GoToDetailScreen(event)) }
                        }
                    }
                }
                if (uiState.passedItems.isNotEmpty()) {
                    item { SectionLabel("Anteriores") }
                    items(uiState.passedItems, key = { it.id }) { event ->
                        SwipeableCard(
                            onDeleteEvent = { onHandleAction(Action.DeleteEvent(event)) }
                        ) {
                            EventCard(event) { onHandleAction(Action.GoToDetailScreen(event)) }
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewEventListContent() {
    CountdownAppTheme {
        EventListContent(
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