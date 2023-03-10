package com.example.countdownapp.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countdownapp.ui.components.Toolbar
import com.example.countdownapp.ui.theme.CountdownAppTheme

@Composable
fun CountdownDetailRoute(
    viewModel: CountdownDetailViewModel = viewModel(),
    onBackPress: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CountDownDetailScreen(
        uiState = uiState,
        onBackPress = { onBackPress() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountDownDetailScreen(
    uiState: DetailUiState,
    onBackPress: () -> Unit
) {
    Scaffold(
        topBar = {
            Toolbar(
                title = uiState.eventName,
                onBackPress = { onBackPress() }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Faltan",
                    fontSize = 24.sp
                )
                Text(
                    text = uiState.remainingTime,
                    fontSize = 200.sp,
                )
                Text(
                    text = uiState.remainingPeriod,
                    fontSize = 24.sp
                )
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun PrevCountDownDetailScreen() {
    CountdownAppTheme {
        CountDownDetailScreen(
            uiState = DetailUiState(
                eventName = "Event Name",
                remainingPeriod = "Period",
                remainingTime = "20"
            )
        ) {}
    }
}