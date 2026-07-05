package com.ulises.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

val colors
    @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme

val shapes
    @Composable @ReadOnlyComposable get() = MaterialTheme.shapes

val typography
    @Composable @ReadOnlyComposable get() = MaterialTheme.typography