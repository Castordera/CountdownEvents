@file:OptIn(ExperimentalMaterial3Api::class)

package com.ulises.list.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.TimeCalculationResponse
import com.ulises.common.resources.R
import com.ulises.components.Loading
import com.ulises.preview_data.MOCK_TIME_CALCULATION_LIST_RESPONSE
import com.ulises.theme.CountdownAppTheme

@Composable
fun MainBottomSheetDialog(
    viewModel: BottomSheetViewModel = hiltViewModel(),
    onDismiss: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(androidx.compose.ui.platform.LocalLifecycleOwner.current)

    val state = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = onDismiss,
    ) {
        BottomSheetContent(uiState = uiState)
    }
}

@Composable
private fun BottomSheetContent(
    modifier: Modifier = Modifier,
    uiState: BottomSheetViewModel.UiState,
) {
    if (uiState.times == null) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center,
        ) {
            Loading()
        }
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(uiState.times) { timeCalculation ->
            TimeDataItem(data = timeCalculation)
            HorizontalDivider(modifier = Modifier.padding(end = 32.dp, top = 8.dp))
        }
    }
}

@Composable
private fun TimeDataItem(
    data: TimeCalculationResponse,
) {
    val (title, message) = dataTimeContent(data = data)
    Column {
        Text(text = title)
        Text(text = message)
    }
}

@Composable
@ReadOnlyComposable
fun dataTimeContent(data: TimeCalculationResponse): Pair<String, String> {
    return when (data) {
        is TimeCalculationResponse.Today -> {
            stringResource(id = R.string.main_screen_today_label) to data.date
        }

        is TimeCalculationResponse.Month -> {
            stringResource(id = R.string.main_screen_month_label) to stringResource(
                id = R.string.main_screen_month_content,
                data.currentDay,
                data.daysLeft
            )
        }

        is TimeCalculationResponse.Season -> {
            stringResource(id = R.string.main_screen_season_label) to stringResource(
                id = R.string.main_screen_season_content,
                data.current,
                data.daysLeft
            )
        }

        is TimeCalculationResponse.Week -> {
            stringResource(id = R.string.main_screen_week_label) to stringResource(
                id = R.string.main_screen_week_content,
                data.current,
                data.left
            )
        }

        is TimeCalculationResponse.Year -> {
            stringResource(id = R.string.main_screen_year_label) to stringResource(
                id = R.string.main_screen_year_content,
                data.currentDay,
                data.daysLeft
            )
        }
    }
}

@Preview
@Composable
private fun PrevMainBottomSheetDialog() {
    CountdownAppTheme {
        Surface {
            BottomSheetContent(
                uiState = BottomSheetViewModel.UiState(
                    times = MOCK_TIME_CALCULATION_LIST_RESPONSE
                )
            )
        }
    }
}

@Preview
@Composable
private fun PrevMainBottomSheetDialogNull() {
    CountdownAppTheme {
        Surface {
            BottomSheetContent(
                uiState = BottomSheetViewModel.UiState()
            )
        }
    }
}