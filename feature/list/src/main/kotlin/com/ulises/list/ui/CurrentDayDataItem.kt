package com.ulises.list.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulises.date_utils.toHumanReadable
import com.ulises.theme.CountdownAppTheme
import java.time.LocalDate
import java.time.temporal.ChronoField

private const val TOTAL_DAYS_OF_YEAR = 52

@Composable
fun CurrentDayDataItem() {
    val date = LocalDate.now()
    val finalDate = LocalDate.of(date.year, 12, 31)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = "Today is ${date.toHumanReadable()}")
        Text(text = "Han pasado ${date.dayOfYear} dias y faltan ${finalDate.dayOfYear - date.dayOfYear - 1}")
        Text(text = "Han pasado ${date.get(ChronoField.ALIGNED_WEEK_OF_YEAR)} semanas y faltan ${TOTAL_DAYS_OF_YEAR-date.get(ChronoField.ALIGNED_WEEK_OF_YEAR)}")
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Prev_CurrentDayDataItem() {
    CountdownAppTheme {
        Surface {
            CurrentDayDataItem()
        }
    }
}