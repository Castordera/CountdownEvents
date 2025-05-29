package com.ulises.list.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.domain.models.YearsData

@Composable
fun YearListComponent(
    years: YearsData,
    onClick: (String) -> Unit = {},
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
    ) {
        items(years.items) { year ->
            FilterChip(
                selected = years.selected == year,
                onClick = { onClick(year) },
                label = { Text(text = year) }
            )
        }
    }
}