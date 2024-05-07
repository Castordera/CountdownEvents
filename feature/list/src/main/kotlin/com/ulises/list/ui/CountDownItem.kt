@file:OptIn(ExperimentalFoundationApi::class)

package com.ulises.list.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.CountdownDate
import com.example.domain.models.DateHandler
import com.ulises.date_utils.remainingTime
import com.ulises.date_utils.toHumanReadable
import com.ulises.list.R
import com.ulises.preview_data.listItemsPreview
import com.ulises.theme.CountdownAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountDownItemList(
    modifier: Modifier = Modifier,
    item: CountdownDate,
    isSelectionMode: Boolean,
    isSelected: Boolean,
    onClick: (CountdownDate) -> Unit = {},
    onLongClick: (CountdownDate) -> Unit = {},
    onCountdownClick: (CountdownDate) -> Unit = {},
) {
    var dateHandler by remember { mutableStateOf(DateHandler(false, "", "")) }

    LaunchedEffect(item.dateDisplayType) {
        dateHandler = item.remainingTime
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick(item) },
                onLongClick = { onLongClick(item) }
            )
            .drawBehind {
                if (isSelected) {
                    drawRoundRect(
                        color = Color.Gray,
                        cornerRadius = CornerRadius(10f, 10f),
                        alpha = 0.3f,
                    )
                }
            }
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedVisibility(visible = isSelectionMode) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_circle),
                        contentDescription = null
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = item.name,
                    fontSize = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.dateToCountdown.toHumanReadable(true),
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
    }
}

@Composable
fun CountDownItemGrid(
    modifier: Modifier = Modifier,
    item: CountdownDate,
    onClick: (CountdownDate) -> Unit = {},
    onDelete: (CountdownDate) -> Unit = {},
    onCountdownClick: (CountdownDate) -> Unit = {},
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
            Text(text = item.dateToCountdown.toHumanReadable(true))
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
                    item = listItemsPreview[1],
                    isSelected = false,
                    isSelectionMode = false,
                )
                CountDownItemList(
                    item = listItemsPreview[1],
                    isSelected = false,
                    isSelectionMode = true,
                )
                CountDownItemList(
                    item = listItemsPreview[1],
                    isSelected = true,
                    isSelectionMode = true,
                )
            }
        }
    }
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
fun PrevCountDownItemGrid() {
    CountdownAppTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CountDownItemGrid(
                    item = listItemsPreview[1],
                )
            }
        }
    }
}