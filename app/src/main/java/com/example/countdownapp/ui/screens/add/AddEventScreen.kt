package com.example.countdownapp.ui.screens.add

import android.app.DatePickerDialog
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countdownapp.R
import com.example.countdownapp.ui.common.createCalendarDialog
import com.ulises.components.toolbars.Toolbar
import com.ulises.theme.CountdownAppTheme

@Composable
fun AddEventRoute(
    viewModel: AddEventViewModel = viewModel(),
    onBackPress: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val dialog = createCalendarDialog(LocalContext.current) { year, month, day ->
        viewModel.onDatePicked(year, month, day)
    }

    if (uiState.goBack) {
        LaunchedEffect(Unit) {
            onBackPress()
        }
    }

    AddEventScreen(
        calendarDialog = dialog,
        uiState = uiState,
        onUpdateEventName = viewModel::onEventNameChanged,
        onSaveEvent = viewModel::onSaveEvent,
        onBackPress = { onBackPress() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    calendarDialog: DatePickerDialog,
    uiState: AddUiState,
    onUpdateEventName: (String) -> Unit,
    onSaveEvent: () -> Unit,
    onBackPress: () -> Unit
) {
    Scaffold(
        topBar = {
            Toolbar(
                title = "Add new Countdown",
                onBackPress = { onBackPress() }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
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
                    text = uiState.date,
                    fontSize = 50.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedButton(
                    onClick = { calendarDialog.show() },
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
            calendarDialog = createCalendarDialog(LocalContext.current) { _, _, _ -> },
            uiState = AddUiState(
                date = "Hoy mero"
            ),
            onUpdateEventName = {},
            onSaveEvent = {},
            onBackPress = {}
        )
    }
}