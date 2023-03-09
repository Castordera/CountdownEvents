package com.example.countdownapp.ui.screens.detail

import android.content.res.Configuration
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
import com.example.countdownapp.ui.common.remainingPeriod
import com.example.countdownapp.ui.common.remainingTime
import com.example.countdownapp.ui.components.Toolbar
import com.example.countdownapp.ui.theme.CountdownAppTheme
import com.example.domain.models.CountdownDate

@Composable
fun CountdownDetailRoute(
    title: String,
    viewModel: CountdownDetailViewModel = viewModel(),
    onBackPress: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CountDownDetailScreen(
        title = title,
        countdownDate = uiState.item,
        onBackPress = { onBackPress() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountDownDetailScreen(
    title: String,
    countdownDate: CountdownDate,
    onBackPress: () -> Unit
) {
    Scaffold(
        topBar = {
            Toolbar(
                title = title,
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
                Text(text = "Faltan")
                Text(
                    text = countdownDate.remainingTime,
                    fontSize = 200.sp,
                )
                Text(text = countdownDate.remainingPeriod)
            }
        }
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true)
@Composable
fun PrevCountDownDetailScreen() {
    CountdownAppTheme {
        CountDownDetailScreen(
            title = "Name event",
            countdownDate = CountdownDate(
                name = "Bebecita",
                dateToCountdown = "2023-05-29T23:25:14.697982"
            )
        ) {}
    }
}