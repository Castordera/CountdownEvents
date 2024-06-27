package com.ulises.event_detail.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.DateHandler
import com.example.domain.models.TimePeriod
import com.ulises.components.Loading
import com.ulises.components.screens.DefaultErrorScreen
import com.ulises.components.toolbars.Toolbar
import com.ulises.components.toolbars.ToolbarItem
import com.ulises.date_utils.remainingTime
import com.ulises.event_detail.R
import com.ulises.event_detail.models.DetailUiState
import com.ulises.preview_data.listItemsPreview
import com.ulises.theme.CountdownAppTheme

@Composable
fun CountdownDetailRoute(
    viewModel: CountdownDetailViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onEditItem: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(androidx.compose.ui.platform.LocalLifecycleOwner.current)

    CountDownDetailScreen(
        uiState = uiState,
        onEditItem = onEditItem,
        onBackPress = onBackPress,
    )
}

@Composable
private fun CountDownDetailScreen(
    uiState: DetailUiState,
    onEditItem: (itemId: String) -> Unit = {},
    onBackPress: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            Toolbar(
                onBackPress = { onBackPress() },
                actions = {
                    ToolbarItem(
                        imageVector = Icons.Filled.Edit,
                        description = "Edit",
                        onClick = { onEditItem(uiState.countdownDate?.id.orEmpty()) }
                    )
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (uiState.error.isNullOrEmpty()) {
                DetailComponent(uiState = uiState)
            } else {
                DefaultErrorScreen(
                    imageRes = com.ulises.common.resources.R.drawable.ic_error,
                    text = uiState.error
                )
            }
        }
    }
}

@Composable
private fun DetailComponent(
    uiState: DetailUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (uiState.countdownDate == null) {
            Loading()
            return
        }

        val dateHandler by remember { mutableStateOf(uiState.countdownDate.remainingTime) }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = uiState.countdownDate.name,
            textAlign = TextAlign.Center,
            fontSize = 22.sp
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (uiState.countdownDate.remainingTime.isToday) {
                Text(
                    text = stringResource(id = com.ulises.common.resources.R.string.main_screen_label_today),
                    fontSize = 80.sp,
                )
            } else {
                Text(
                    text = if (dateHandler.isInPast) {
                        stringResource(id = com.ulises.common.resources.R.string.detail_screen_label_time_passed)
                    } else {
                        stringResource(id = com.ulises.common.resources.R.string.detail_screen_label_time_remaining)
                    },
                    fontSize = 24.sp
                )
                Text(
                    text = "%,d".format(dateHandler.value),
                    fontSize = 150.sp,
                )
                Text(
                    text = stringResource(id = com.ulises.common.resources.R.string.detail_screen_label_time_bottom, getStringTimeLabel(dateHandler = dateHandler)),
                    fontSize = 24.sp
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Created: ${uiState.countdownDate.createdAt}",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = "Id: ${uiState.countdownDate.id}",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
@ReadOnlyComposable
fun getStringTimeLabel(dateHandler: DateHandler): String {
    return when (dateHandler.periodType) {
        TimePeriod.NONE -> ""
        TimePeriod.YEAR -> pluralStringResource(
            id = com.ulises.common.resources.R.plurals.time_label_year,
            count = dateHandler.value
        )
        TimePeriod.WEEK -> pluralStringResource(
            id = com.ulises.common.resources.R.plurals.time_label_week,
            count = dateHandler.value
        )
        TimePeriod.DAY -> pluralStringResource(
            id = com.ulises.common.resources.R.plurals.time_label_day,
            count = dateHandler.value
        )
        TimePeriod.HOUR -> pluralStringResource(
            id = com.ulises.common.resources.R.plurals.time_label_hour,
            count = dateHandler.value
        )
        TimePeriod.MINUTE -> pluralStringResource(
            id = com.ulises.common.resources.R.plurals.time_label_minute,
            count = dateHandler.value
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrevCountDownDetailScreen() {
    CountdownAppTheme {
        CountDownDetailScreen(
            uiState = DetailUiState(
                countdownDate = listItemsPreview[1]
            )
        ) {}
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrevCountDownDetailScreenError() {
    CountdownAppTheme {
        CountDownDetailScreen(
            uiState = DetailUiState(
                error = "error here"
            ),
        )
    }
}