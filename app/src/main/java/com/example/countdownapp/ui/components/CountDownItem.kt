package com.example.countdownapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
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
import com.example.countdownapp.ui.common.remainingTime
import com.example.countdownapp.ui.common.toReadableDate
import com.example.countdownapp.ui.screens.utils.listItemsPreview
import com.example.countdownapp.ui.theme.CountdownAppTheme
import com.example.domain.models.CountdownDate
import com.example.domain.models.DateHandler

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountDownItemList(
    modifier: Modifier = Modifier,
    item: CountdownDate,
    onClick: (CountdownDate) -> Unit
) {
    var dateHandler by remember { mutableStateOf(DateHandler(false, "", "")) }

    LaunchedEffect(Unit) {
        dateHandler = item.remainingTime
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = { onClick(item) },
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
                    fontSize = 20.sp
                )
                Text(
                    text = item.toReadableDate,
                    fontStyle = FontStyle.Italic
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = (if (dateHandler.isInPast) "Hace " else "") + dateHandler.periodType,
                    fontSize = 12.sp
                )
                Text(
                    text = dateHandler.value,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Divider(modifier = Modifier.padding(horizontal = 8.dp))
    }
}

@Composable
fun CountDownItemGrid(
    modifier: Modifier = Modifier,
    item: CountdownDate,
    onClick: (CountdownDate) -> Unit
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
                .clickable { onClick(item) }
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
                text = (if (dateHandler.isInPast) "Hace " else "") + dateHandler.periodType,
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
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CountDownItemList(
                item = listItemsPreview[0],
                onClick = {}
            )
            CountDownItemGrid(
                item = listItemsPreview[1],
                onClick = {}
            )
        }
    }
}