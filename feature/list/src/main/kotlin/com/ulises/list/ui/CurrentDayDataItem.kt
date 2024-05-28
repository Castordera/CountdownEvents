package com.ulises.list.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.date_utils.toHumanReadable
import com.ulises.theme.CountdownAppTheme
import java.time.LocalDate
import java.time.temporal.ChronoField

private const val TOTAL_WEEKS_OF_YEAR = 52

@Composable
fun CurrentDayDataItem() {
    val date = LocalDate.now()
    val finalDate = LocalDate.of(date.year, 12, 31)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
            Text(
                text = date.toHumanReadable().capitalize(Locale.current),
                fontSize = 22.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(text = buildAnnotatedString {
                append("Dias: ")
                pushStyle(SpanStyle(fontSize = 18.sp))
                append("${date.dayOfYear}")
            })
            Text(text = buildAnnotatedString {
                append("Faltan: ")
                pushStyle(SpanStyle(fontSize = 18.sp))
                append("${finalDate.dayOfYear - date.dayOfYear - 1}")
            })
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(text = buildAnnotatedString {
                append("Semana: ")
                pushStyle(SpanStyle(fontSize = 18.sp))
                append("${date.get(ChronoField.ALIGNED_WEEK_OF_YEAR)}")
            })
            Text(text = buildAnnotatedString {
                append("Faltan: ")
                pushStyle(SpanStyle(fontSize = 18.sp))
                append("${TOTAL_WEEKS_OF_YEAR - date.get(ChronoField.ALIGNED_WEEK_OF_YEAR)}")
            })
        }
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