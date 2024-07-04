package com.ulises.components.toolbars

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ulises.theme.CountdownAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    title: String = "",
    onBackPress: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (onBackPress == null) Unit
            else {
                IconButton(onClick = { onBackPress() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        actions = actions
    )
}

@Preview
@Composable
fun PrevToolbar() {
    CountdownAppTheme {
        Toolbar(
            title = "Screen name",
            actions = {
                ToolbarItem(iconRes = com.ulises.common.resources.R.drawable.ic_error)
                ToolbarItem(iconRes = com.ulises.common.resources.R.drawable.ic_error)
                ToolbarItem(iconRes = com.ulises.common.resources.R.drawable.ic_error)
            }
        )
    }
}

@Preview
@Composable
fun PrevToolbarBack() {
    CountdownAppTheme {
        Toolbar(
            title = "Screen name",
            onBackPress = {}
        )
    }
}