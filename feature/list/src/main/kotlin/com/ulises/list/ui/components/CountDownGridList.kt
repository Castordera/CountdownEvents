package com.ulises.list.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.CountdownDate
import com.ulises.preview_data.listItemsPreview
import com.ulises.theme.CountdownAppTheme

@Composable
internal fun CountDownGridList(
    modifier: Modifier = Modifier,
    columns: Int = 2,
    items: List<CountdownDate>,
    passedItems: List<CountdownDate>,
    onClickItem: (CountdownDate) -> Unit = {},
    onDeleteItem: (CountdownDate) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        modifier = modifier
    ) {
        item(
            span = { GridItemSpan(columns) }
        ) {
            CurrentDayDataItem()
        }
        items(
            items = items,
            key = { "${it.name}_${it.id}" }
        ) {
            CountDownItemGrid(
                item = it,
                onClick = onClickItem,
                onDelete = onDeleteItem,
                onCountdownClick = {},
            )
        }
        if (passedItems.isNotEmpty()) {
            item(
                span = { GridItemSpan(columns) }
            ) {
                Text(
                    text = stringResource(id = com.ulises.common.resources.R.string.main_screen_label_passed_events),
                    modifier = Modifier.padding(16.dp)
                )
            }
            items(
                items = passedItems,
                key = { "${it.name}_${it.id}" }
            ) {
                CountDownItemGrid(
                    item = it,
                    onClick = onClickItem,
                    onDelete = onDeleteItem,
                    onCountdownClick = {},
                )
            }
        }
    }
}

@Preview
@Composable
private fun Prev_List() {
    CountdownAppTheme {
        Surface {
            CountDownGridList(
                items = listItemsPreview,
                passedItems = emptyList()
            )
        }
    }
}