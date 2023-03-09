package com.example.countdownapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.countdownapp.ui.common.remainingPeriod
import com.example.countdownapp.ui.common.remainingTime
import com.example.countdownapp.ui.common.toReadableDate
import com.example.countdownapp.ui.theme.CountdownAppTheme
import com.example.domain.models.CountdownDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountDownItem(
    modifier: Modifier = Modifier,
    item: CountdownDate,
    onLongClick: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() }
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = item.name)
            Text(text = "Date: ${item.toReadableDate}")
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = item.remainingPeriod,
                fontSize = 12.sp
            )
            Text(
                text = item.remainingTime,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
fun PrevCountDownItem() {
    CountdownAppTheme {
        CountDownItem(
            item = CountdownDate(
                name = "Christmas",
                dateToCountdown = "2024-03-08T23:25:14.697982"
            ),
            onLongClick = {},
            onClick = {}
        )
    }
}