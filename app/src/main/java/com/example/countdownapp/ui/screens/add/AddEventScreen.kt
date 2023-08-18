package com.example.countdownapp.ui.screens.add

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countdownapp.R
import com.example.countdownapp.ui.common.AppDatePicker
import com.example.countdownapp.ui.common.toHumanReadable
import com.example.countdownapp.ui.common.toMillis
import com.ulises.components.toolbars.Toolbar
import com.ulises.theme.CountdownAppTheme
import java.time.LocalDateTime

@Composable
fun AddEventRoute(
    viewModel: AddEventViewModel = viewModel(),
    onBackPress: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.goBack) {
        LaunchedEffect(Unit) {
            onBackPress()
        }
    }

    AddEventScreen(
        uiState = uiState,
        onCalendarDateSelected = viewModel::onDateSelected,
        onCalendarChangeVisibility = viewModel::onChangeCalendarVisibility,
        onUpdateEventName = viewModel::onEventNameChanged,
        onSaveEvent = viewModel::onSaveEvent,
        onBackPress = onBackPress
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    uiState: AddUiState,
    onCalendarDateSelected: (Long?) -> Unit,
    onCalendarChangeVisibility: (Boolean) -> Unit,
    onUpdateEventName: (String) -> Unit,
    onSaveEvent: () -> Unit,
    onBackPress: () -> Unit
) {
    Scaffold(
        topBar = {
            Toolbar(
                title = "Add new Countdown",
                onBackPress = onBackPress
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            AppDatePicker(
                datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = uiState.dateTime.toMillis()
                ),
                isVisible = uiState.dateDialogVisible,
                onCancelClick = { onCalendarChangeVisibility(false) },
                onDateSelected = onCalendarDateSelected
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = uiState.eventName,
                    onValueChange = onUpdateEventName,
                    label = { Text("Event Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = uiState.dateTime.toHumanReadable(),
                    fontSize = 50.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedButton(
                    onClick = { onCalendarChangeVisibility(true) },
                    border = null
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            8.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = null
                        )
                        Text(text = "Change day")
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = onSaveEvent) {
                    Text(
                        text = "Save Event",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevAddEventScreen() {
    CountdownAppTheme {
        AddEventScreen(
            uiState = AddUiState(
                dateTime = LocalDateTime.now()
            ),
            onUpdateEventName = {},
            onCalendarDateSelected = {},
            onCalendarChangeVisibility = {},
            onSaveEvent = {},
            onBackPress = {}
        )
    }
}