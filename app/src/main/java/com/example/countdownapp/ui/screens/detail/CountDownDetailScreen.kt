package com.example.countdownapp.ui.screens.detail

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countdownapp.R
import com.example.countdownapp.ui.common.remainingTime
import com.ulises.components.screens.DefaultErrorScreen
import com.ulises.components.toolbars.Toolbar
import com.ulises.components.toolbars.TopBarItem
import com.ulises.preview_data.listItemsPreview
import com.ulises.theme.CountdownAppTheme

@Composable
fun CountdownDetailRoute(
    viewModel: CountdownDetailViewModel = viewModel(),
    onBackPress: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CountDownDetailScreen(
        uiState = uiState,
        onBackPress = onBackPress
    )
}

@Composable
fun CountDownDetailScreen(
    uiState: DetailUiState,
    actions: List<TopBarItem> = emptyList(),
    onBackPress: () -> Unit
) {
    Scaffold(
        topBar = {
            Toolbar(
                title = uiState.countdownDate?.name.orEmpty(),
                onBackPress = { onBackPress() },
                actions = actions
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
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "Faltan",
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Created: ${uiState.countdownDate?.createdAt}",
                fontSize = 16.sp,
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
                countdownDate = listItemsPreview[0],
                error = null
            ),
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