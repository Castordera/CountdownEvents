package com.ulises.event_detail.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.common.resources.R
import com.ulises.components.Loading
import com.ulises.components.screens.DefaultErrorScreen
import com.ulises.components.toolbars.Toolbar
import com.ulises.components.toolbars.ToolbarItem
import com.ulises.date_utils.remainingTime
import com.ulises.date_utils.toHumanReadable
import com.ulises.event_detail.models.DayDetail
import com.ulises.event_detail.models.DetailUiState
import com.ulises.preview_data.getMockCountDown
import com.ulises.theme.CountdownAppTheme

@Composable
internal fun CountDownDetailScreen(
    uiState: () -> DetailUiState,
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
                        onClick = { onEditItem(uiState().countdownDate?.id.orEmpty()) }
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
            if (uiState().error.isNullOrEmpty()) {
                DetailComponent(uiState = uiState)
            } else {
                DefaultErrorScreen(
                    imageRes = R.drawable.ic_error,
                    text = uiState().error ?: ""
                )
            }
        }
    }
}

@Composable
private fun DetailComponent(
    uiState: () -> DetailUiState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (uiState().countdownDate == null || uiState().dayDetail == null) {
            Loading()
            return
        }

        var isEstimationVisible by remember { mutableStateOf(true) }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = uiState().countdownDate!!.name,
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
            if (uiState().countdownDate!!.remainingTime.isToday) {
                Text(
                    text = stringResource(id = R.string.main_screen_label_today),
                    fontSize = 80.sp,
                )
            } else {
                AnimatedContent(
                    targetState = isEstimationVisible,
                    label = "Animated Content",
                ) { targetState ->
                    when (targetState) {
                        true -> EstimationComponent(uiState().countdownDate!!)
                        false -> CalculationComponent(uiState().dayDetail!!)
                    }
                }
                IconButton(
                    onClick = { isEstimationVisible = !isEstimationVisible },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.swap_horiz),
                        contentDescription = null
                    )
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = uiState().countdownDate!!.dateToCountdown.toHumanReadable(includeDay = true),
                fontSize = 20.sp,
            )
            Text(
                text = "Created: ${uiState().countdownDate!!.createdAt}",
                fontSize = 8.sp,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = "Id: ${uiState().countdownDate!!.id}",
                fontSize = 8.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PrevCountDownDetailScreen() {
    CountdownAppTheme {
        CountDownDetailScreen(
            uiState = {
                DetailUiState(
                    countdownDate = getMockCountDown(
                        name = "This is a huge value to have as name",
                        date = "2023-12-08T00:00:00"
                    ),
                    dayDetail = DayDetail(
                        years = 1,
                        days = 10,
                        hours = 10,
                        minutes = 10,
                        isPast = false,
                    )
                )
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun PrevCountDownDetailScreenError() {
    CountdownAppTheme {
        CountDownDetailScreen(
            uiState = {
                DetailUiState(
                    error = "Error happened and this screen was displayed"
                )
            },
        )
    }
}