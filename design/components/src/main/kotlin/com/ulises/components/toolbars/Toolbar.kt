package com.ulises.components.toolbars

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String = "",
    onBackPress: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            if (onBackPress == null) Unit
            else {
                IconBtn({ onBackPress() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        },
        actions = actions,
    )
}

@Deprecated("Use IconBtn instead")
@Composable
fun AppTopBarIcon(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(36.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surface)
            .border(0.5.dp, MaterialTheme.colorScheme.outlineVariant, MaterialTheme.shapes.large)
    ) { icon() }
}

@Composable
fun IconBtn(
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surface)
            .border(0.5.dp, MaterialTheme.colorScheme.outlineVariant, MaterialTheme.shapes.large)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        icon()
    }
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
        Column {
            Toolbar(
                title = "Screen name",
                onBackPress = {}
            )
            AppTopBar(
                onBackPress = {},
            ) {
                AppTopBarIcon({ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                    )
                }
                IconBtn({}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                    )
                }
                IconBtn({}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}