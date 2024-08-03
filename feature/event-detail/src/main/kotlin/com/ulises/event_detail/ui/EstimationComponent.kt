package com.ulises.event_detail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.domain.models.CountdownDate
import com.example.domain.models.DateHandler
import com.example.domain.models.TimePeriod
import com.ulises.common.resources.R
import com.ulises.date_utils.remainingTime
import com.ulises.preview_data.getMockCountDown
import com.ulises.theme.CountdownAppTheme

@Composable
fun EstimationComponent(
    countdownDate: CountdownDate,
) {
    val dateHandler by remember(countdownDate) {
        mutableStateOf(countdownDate.remainingTime)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (dateHandler.isInPast) {
                stringResource(id = R.string.detail_screen_label_time_passed)
            } else {
                stringResource(id = R.string.detail_screen_label_time_remaining)
            },
            fontSize = 24.sp
        )
        Text(
            text = "%,d".format(dateHandler.value),
            fontSize = 150.sp,
        )
        Text(
            text = stringResource(
                id = R.string.detail_screen_label_time_bottom,
                getStringTimeLabel(dateHandler = dateHandler)
            ),
            fontSize = 24.sp
        )
    }
}

@Composable
@ReadOnlyComposable
fun getStringTimeLabel(dateHandler: DateHandler): String {
    return when (dateHandler.periodType) {
        TimePeriod.NONE -> ""
        TimePeriod.YEAR -> pluralStringResource(
            id = R.plurals.time_label_year,
            count = dateHandler.value
        )

        TimePeriod.WEEK -> pluralStringResource(
            id = R.plurals.time_label_week,
            count = dateHandler.value
        )

        TimePeriod.DAY -> pluralStringResource(
            id = R.plurals.time_label_day,
            count = dateHandler.value
        )

        TimePeriod.HOUR -> pluralStringResource(
            id = R.plurals.time_label_hour,
            count = dateHandler.value
        )

        TimePeriod.MINUTE -> pluralStringResource(
            id = R.plurals.time_label_minute,
            count = dateHandler.value
        )
    }
}

@Preview
@Composable
private fun PrevComposable() {
    CountdownAppTheme {
        EstimationComponent(
            countdownDate = getMockCountDown(
                name = "This is a huge value to have as name",
                date = "2023-12-08T00:00:00"
            ),
        )
    }
}