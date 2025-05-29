package com.ulises.list.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.date_utils.toHumanReadable
import com.ulises.theme.CountdownAppTheme
import java.time.LocalDate

@Composable
fun CurrentDayDataItem(
    date: LocalDate = LocalDate.now(),
    onClickMoreData: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 8.dp)
    ) {
        Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
        Text(
            text = date.toHumanReadable("EEE, dd MMMM yyyy"),
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onClickMoreData) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
        }
    }
}

@PreviewLightDark
@Composable
private fun Prev_CurrentDayDataItem() {
    CountdownAppTheme {
        Surface {
            CurrentDayDataItem()
        }
    }
}