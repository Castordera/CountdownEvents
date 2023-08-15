package com.ulises.components.toolbars

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ulises.components.R
import com.ulises.theme.CountdownAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    title: String,
    onBackPress: (() -> Unit)? = null,
    actions: List<TopBarItem> = emptyList()
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
        actions = {
            actions.forEach { item ->
                IconButton(onClick = { item.onClick() }) {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.description
                    )
                }
            }
        })
}

@Preview
@Composable
fun PrevToolbar() {
    CountdownAppTheme {
        Toolbar(
            title = "Screen name",
            actions = listOf(
                TopBarItem("Description", R.drawable.ic_add_24) {},
                TopBarItem("Description", R.drawable.ic_add_24) {},
                TopBarItem("Description", R.drawable.ic_add_24) {}
            )
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