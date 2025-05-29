package com.ulises.list.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.CountdownDate
import com.ulises.common.resources.R
import com.ulises.preview_data.listItemsPreview
import com.ulises.theme.CountdownAppTheme

@Composable
internal fun CountDownList(
    modifier: Modifier = Modifier,
    items: List<CountdownDate>,
    passedItems: List<CountdownDate>,
    selectedItems: Set<String>,
    isSelectionMode: Boolean,
    onClickItem: (CountdownDate) -> Unit = {},
    onLongClickItem: (CountdownDate) -> Unit = {},
    onCountdownClick: (CountdownDate) -> Unit = {},
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier
    ) {
        items(
            items = items,
            key = { it.id }
        ) {
            CountDownItemList(
                item = it,
                isSelectionMode = isSelectionMode,
                isSelected = selectedItems.contains(it.id),
                onClick = onClickItem,
                onLongClick = onLongClickItem,
                onCountdownClick = onCountdownClick,
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
        }
        if (passedItems.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(id = R.string.main_screen_label_passed_events),
                    modifier = Modifier.padding(16.dp)
                )
            }
            items(
                items = passedItems,
                key = { it.id }
            ) {
                CountDownItemList(
                    item = it,
                    isSelectionMode = isSelectionMode,
                    isSelected = selectedItems.contains(it.id),
                    onClick = onClickItem,
                    onLongClick = onLongClickItem,
                    onCountdownClick = onCountdownClick,
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
    }
}

@Preview
@Composable
private fun Prev_CountDownList() {
    CountdownAppTheme {
        Surface {
            CountDownList(
                items = listItemsPreview,
                passedItems = emptyList(),
                selectedItems = emptySet(),
                isSelectionMode = false,
            )
        }
    }
}