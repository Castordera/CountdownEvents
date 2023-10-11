package com.ulises.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ulises.theme.CountdownAppTheme

@Composable
fun Loading(
    innerSize: Dp = 20.dp,
    outerSize: Dp = 30.dp
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(innerSize)
        )
        CircularProgressIndicator(
            modifier = Modifier.size(outerSize)
        )
    }
}

@Preview
@Composable
fun PrevLoading() {
    CountdownAppTheme {
        Surface {
            Loading()
        }
    }
}