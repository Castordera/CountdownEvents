package com.ulises.components.toolbars

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ulises.components.R
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
                        painter = painterResource(id = R.drawable.ic_arrow_back),
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
                ToolbarItem(iconRes = R.drawable.ic_add_24)
                ToolbarItem(iconRes = R.drawable.ic_add_24)
                ToolbarItem(iconRes = R.drawable.ic_add_24)
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