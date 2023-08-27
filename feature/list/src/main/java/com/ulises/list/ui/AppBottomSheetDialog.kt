@file:OptIn(ExperimentalMaterial3Api::class)

package com.ulises.list.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulises.components.buttons.TextRadioButton
import com.ulises.list.common.CountdownSortType
import com.ulises.list.common.radioOptionsPreview
import com.ulises.theme.CountdownAppTheme

@Composable
fun MainBottomSheetDialog(
    radioOptions: List<CountdownSortType>,
    selected: CountdownSortType,
    onClickItem: (CountdownSortType) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val state = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = onDismiss
    ) {
        Text(
            text = "Sort By",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .selectableGroup()
                .padding(16.dp)
        ) {
            radioOptions.forEach { item ->
                TextRadioButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.name,
                    selected = selected == item,
                    onClick = { onClickItem(CountdownSortType.valueOf(it)) }
                )
            }
        }
    }
}

@Preview
@Composable
fun PrevMainBottomSheetDialog() {
    CountdownAppTheme {
        MainBottomSheetDialog(
            radioOptions = radioOptionsPreview,
            selected = CountdownSortType.NORMAL
        )
    }
}