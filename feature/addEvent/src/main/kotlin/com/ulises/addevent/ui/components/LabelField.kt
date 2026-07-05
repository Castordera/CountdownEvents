package com.ulises.addevent.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ulises.theme.colors
import com.ulises.theme.typography

@Composable
internal fun LabelField(text: String) {
    Text(
        text = text.uppercase(),
        style = typography.labelSmall,
        color = colors.onSurfaceVariant,
        modifier = Modifier.padding(bottom = 6.dp),
    )
}