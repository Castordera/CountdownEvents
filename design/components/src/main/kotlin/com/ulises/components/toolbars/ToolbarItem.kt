package com.ulises.components.toolbars

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ulises.components.R
import com.ulises.theme.CountdownAppTheme

@Composable
fun ToolbarItem(
    isVisible: Boolean = true,
    onClick: () -> Unit = {},
    description: String = "",
    @DrawableRes iconRes: Int,
) {
    if (!isVisible) return

    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = description
        )
    }
}

@Composable
fun ToolbarItem(
    isVisible: Boolean = true,
    onClick: () -> Unit = {},
    description: String = "",
    imageVector: ImageVector
) {
    if (!isVisible) return

    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = description
        )
    }
}

@Preview
@Composable
private fun PrevToolbarItem() {
    CountdownAppTheme {
        Column {
            ToolbarItem(imageVector = Icons.Default.AccountBox)
            ToolbarItem(iconRes = com.ulises.common.resources.R.drawable.ic_error)
        }
    }
}