package com.ulises.event_detail.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulises.components.screens.DefaultErrorScreen
import com.ulises.components.toolbars.Toolbar
import com.ulises.components.toolbars.TopBarItem
import com.ulises.date_utils.remainingTime
import com.ulises.event_detail.R
import com.ulises.event_detail.models.DetailUiState
import com.ulises.preview_data.listItemsPreview
import com.ulises.theme.CountdownAppTheme

@Composable
fun CountdownDetailRoute(
    viewModel: CountdownDetailViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onEditItem: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CountDownDetailScreen(
        uiState = uiState,
        onEditItem = onEditItem,
        onBackPress = onBackPress,
    )
}

@Composable
fun CountDownDetailScreen(
    uiState: DetailUiState,
    actions: List<TopBarItem> = emptyList(),
    onEditItem: (itemId: String) -> Unit = {},
    onBackPress: () -> Unit,
) {
    Scaffold(
        topBar = {
            Toolbar(
                onBackPress = { onBackPress() },
                actions = actions + listOf(
                    TopBarItem(
                        description = "Edit",
                        icon = R.drawable.ic_error,
                        onClick = { onEditItem(uiState.countdownDate?.id.orEmpty()) }
                    )
                )
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (uiState.error.isNullOrEmpty()) {
                DetailComponent(uiState = uiState)
            } else {
                DefaultErrorScreen(
                    imageRes = R.drawable.ic_error,
                    text = uiState.error
                )
            }
        }
    }
}

@Composable
fun DetailComponent(
    uiState: DetailUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = uiState.countdownDate?.name.orEmpty(),
            textAlign = TextAlign.Center,
            fontSize = 22.sp
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = if (uiState.countdownDate?.remainingTime?.isInPast == true) "Hace" else "Faltan",
                fontSize = 24.sp
            )
            Text(
                text = uiState.countdownDate?.remainingTime?.value.orEmpty(),
                fontSize = 200.sp,
            )
            Text(
                text = uiState.countdownDate?.remainingTime?.periodType.orEmpty(),
                fontSize = 24.sp
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Created: ${uiState.countdownDate?.createdAt}",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = "Id: ${uiState.countdownDate?.id}",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevCountDownDetailScreen() {
    CountdownAppTheme {
        CountDownDetailScreen(
            uiState = DetailUiState(
                countdownDate = listItemsPreview[1]
            )
        ) {}
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevCountDownDetailScreenError() {
    CountdownAppTheme {
        CountDownDetailScreen(
            uiState = DetailUiState(
                error = "error here"
            ),
        ) {}
    }
}