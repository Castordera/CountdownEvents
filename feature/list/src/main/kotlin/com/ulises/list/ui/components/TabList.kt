package com.ulises.list.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.domain.models.YearsData
import com.ulises.theme.CountdownAppTheme

@Composable
internal fun ListTabs(
    yearsTabs: YearsData,
    onTabClick: (String) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .padding(horizontal = 22.dp)
            .padding(bottom = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        items(yearsTabs.items, key = { it }) { tab ->
            TabChip(
                label = tab,
                active = yearsTabs.selected == tab,
                onClick = { onTabClick(tab) },
            )
        }
    }
}

@Composable
private fun TabChip(
    label: String,
    active: Boolean,
    onClick: () -> Unit
) {
    val bg = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    val border =
        if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val text =
        if (active) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant

    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .background(bg)
            .border(0.5.dp, border, MaterialTheme.shapes.large)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 5.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = text,
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewListTabs() {
    CountdownAppTheme {
        Surface {
            ListTabs(
                yearsTabs = YearsData(items = listOf("2023", "2024", "2025"), selected = "2023"),
                onTabClick = {},
            )
        }
    }
}