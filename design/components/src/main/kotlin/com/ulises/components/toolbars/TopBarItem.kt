package com.ulises.components.toolbars

import androidx.annotation.DrawableRes

data class TopBarItem(
    val description: String,
    @DrawableRes val icon: Int,
    val isVisible: Boolean = true,
    val onClick: () -> Unit
)