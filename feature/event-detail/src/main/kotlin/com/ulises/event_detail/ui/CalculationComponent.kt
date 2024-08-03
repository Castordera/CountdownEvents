package com.ulises.event_detail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.common.resources.R
import com.ulises.event_detail.models.DayDetail
import com.ulises.theme.CountdownAppTheme

@Composable
fun CalculationComponent(
    detail: DayDetail,
) {
    Column {
        Text(
            text = if (detail.isPast) {
                stringResource(id = R.string.detail_screen_label_time_ago)
            } else {
                stringResource(id = R.string.detail_screen_label_time_remaining)
            },
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if (detail.years > 0) {
            Text(
                text = "Years: ${detail.years}",
                fontSize = 20.sp,
            )
        }
        if (detail.days > 0) {
            Text(
                text = "Days: ${detail.days}",
                fontSize = 20.sp,
            )
        }
        if (detail.hours > 0 ) {
            Text(
                text = "Hours: ${detail.hours}",
                fontSize = 20.sp,
            )
        }
        Text(
            text = "Minutes: ${detail.minutes}",
            fontSize = 20.sp,
        )
    }
}

@Preview
@Composable
private fun PreviewComposable() {
    CountdownAppTheme {
        Surface {
            CalculationComponent(
                DayDetail(
                    years = 1,
                    days = 10,
                    hours = 10,
                    minutes = 10,
                    isPast = false,
                )
            )
        }
    }
}