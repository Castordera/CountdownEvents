package com.ulises.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

@Composable
@ReadOnlyComposable
fun backgroundColor(days: Int): Color {
    val cs = MaterialTheme.colorScheme
    return when {
        days < 0 -> cs.surfaceContainer
        days <= 14 -> cs.surfaceVariant
        days <= 90 -> cs.secondaryContainer
        else -> cs.tertiaryContainer
    }
}

@Composable
@ReadOnlyComposable
fun textColor(days: Int): Color {
    val cs = MaterialTheme.colorScheme
    return when {
        days < 0 -> cs.inverseSurface
        days <= 14 -> cs.secondary
        days <= 90 -> cs.tertiary
        else -> cs.error
    }
}