package com.ulises.addevent

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulises.components.Loading
import com.ulises.components.dialogs.AppDatePicker
import com.ulises.components.toolbars.Toolbar
import com.ulises.date_utils.toHumanReadable
import com.ulises.date_utils.toMillis
import com.ulises.theme.CountdownAppTheme
import java.time.LocalDateTime

@Composable
fun AddEventRoute(
    viewModel: AddEventViewModel = hiltViewModel(),
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
    uiState: UiState,
    onCalendarDateSelected: (Long?) -> Unit,
    onCalendarChangeVisibility: (Boolean) -> Unit,
    onUpdateEventName: (String) -> Unit,
    onSaveEvent: () -> Unit,
    onBackPress: () -> Unit
) {
    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.add_screen_title),
                onBackPress = onBackPress
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            if (uiState.isLoading) {
                Loading(); return@Surface
            }
            uiState.dateTime?.also { date ->
                AppDatePicker(
                    datePickerState = rememberDatePickerState(
                        initialSelectedDateMillis = date.toMillis()
                    ),
                    isVisible = uiState.dateDialogVisible,
                    onCancelClick = { onCalendarChangeVisibility(false) },
                    onDateSelected = onCalendarDateSelected
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = uiState.eventName,
                    onValueChange = onUpdateEventName,
                    label = { Text(stringResource(id = R.string.add_screen_edit_text_event_name_placeholder)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = uiState.dateTime.toHumanReadable(),
                    fontSize = 50.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                TextButton(
                    onClick = { onCalendarChangeVisibility(true) },
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
                        Text(text = stringResource(id = R.string.add_screen_button_change_date))
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onSaveEvent,
                    enabled = uiState.saveButtonEnabled
                ) {
                    Text(
                        text = stringResource(id = R.string.add_screen_button_save_date),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevAddEventScreen() {
    CountdownAppTheme {
        AddEventScreen(
            uiState = UiState(
                isLoading = false,
                dateTime = LocalDateTime.now(),
            ),
            onUpdateEventName = {},
            onCalendarDateSelected = {},
            onCalendarChangeVisibility = {},
            onSaveEvent = {},
            onBackPress = {}
        )
    }
}