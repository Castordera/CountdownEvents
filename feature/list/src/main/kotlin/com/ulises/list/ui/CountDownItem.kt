@file:OptIn(ExperimentalFoundationApi::class)

package com.ulises.list.ui

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.CountdownDate
import com.example.domain.models.DateHandler
import com.ulises.date_utils.remainingTime
import com.ulises.date_utils.toReadableDate
import com.ulises.preview_data.listItemsPreview
import com.ulises.theme.CountdownAppTheme
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountDownItemList(
    modifier: Modifier = Modifier,
    item: CountdownDate,
    onClick: (CountdownDate) -> Unit,
    onDelete: (CountdownDate) -> Unit,
    onCountdownClick: (CountdownDate) -> Unit,
) {
    var dateHandler by remember { mutableStateOf(DateHandler(false, "", "")) }

    LaunchedEffect(item.dateDisplayType) {
        dateHandler = item.remainingTime
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = { onClick(item) },
                    onLongClick = { onDelete(item) }
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.name,
                    fontSize = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.toReadableDate,
                    fontStyle = FontStyle.Italic
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.clickable { onCountdownClick(item) }
            ) {
                Text(
                    text = dateHandler.periodType + (if (dateHandler.isInPast) " ago" else ""),
                    fontSize = 12.sp
                )
                Text(
                    text = dateHandler.value,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
    }
}

@Composable
fun CountDownItemGrid(
    modifier: Modifier = Modifier,
    item: CountdownDate,
    onClick: (CountdownDate) -> Unit,
    onDelete: (CountdownDate) -> Unit,
    onCountdownClick: (CountdownDate) -> Unit,
) {

    var dateHandler by remember { mutableStateOf(DateHandler(false, "", "")) }

    LaunchedEffect(Unit) {
        dateHandler = item.remainingTime
    }

    Card {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = { onClick(item) },
                    onLongClick = { onDelete(item) }
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = item.name,
                fontSize = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = item.toReadableDate)
            Text(
                text = dateHandler.value,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = dateHandler.periodType + (if (dateHandler.isInPast) " ago" else ""),
                fontSize = 12.sp
            )
        }
    }
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
fun PrevCountDownItem() {
    CountdownAppTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CountDownItemList(
                    item = listItemsPreview[0],
                    onClick = {},
                    onDelete = {},
                    onCountdownClick = {},
                )
                CountDownItemList(
                    item = listItemsPreview[1],
                    onClick = {},
                    onDelete = {},
                    onCountdownClick = {},
                )
                CountDownItemGrid(
                    item = listItemsPreview[0],
                    onClick = {},
                    onDelete = {},
                    onCountdownClick = {},
                )
                CountDownItemGrid(
                    item = listItemsPreview[1],
                    onClick = {},
                    onDelete = {},
                    onCountdownClick = {},
                )
            }
        }
    }
}