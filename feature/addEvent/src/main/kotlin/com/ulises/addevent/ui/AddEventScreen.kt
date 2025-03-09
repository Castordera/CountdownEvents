package com.ulises.addevent.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.addevent.model.Actions
import com.ulises.addevent.model.UiState
import com.ulises.components.Loading
import com.ulises.components.dialogs.AppDatePicker
import com.ulises.components.toolbars.Toolbar
import com.ulises.date_utils.toHumanReadable
import com.ulises.date_utils.toMillis
import com.ulises.theme.CountdownAppTheme
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddEventScreen(
    uiState: () -> UiState,
    onGetTextFieldValue: () -> String = { "" },
    onBackPress: () -> Unit = {},
    onActionPerformed: (Actions) -> Unit = {},
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val isDataReady by remember {
        derivedStateOf { onGetTextFieldValue().trim().isNotBlank() }
    }
    var isCalendarVisible by remember { mutableStateOf(false) }

    if (uiState().error != null) {
        LaunchedEffect(uiState().error) {
            snackBarHostState.showSnackbar(uiState().error ?: "")
            onActionPerformed(Actions.DismissError)
        }
    }

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = com.ulises.common.resources.R.string.add_screen_title),
                onBackPress = onBackPress
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            if (uiState().isLoading) {
                Loading(); return@Surface
            }
            uiState().dateTime?.also { date ->
                AppDatePicker(
                    datePickerState = rememberDatePickerState(
                        initialSelectedDateMillis = date.toMillis()
                    ),
                    isVisible = isCalendarVisible,
                    onCancelClick = { isCalendarVisible = false },
                    onDateSelected = { time ->
                        onActionPerformed(Actions.DateSelection(time))
                        isCalendarVisible = false
                    },
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = onGetTextFieldValue(),
                    onValueChange = { text -> onActionPerformed(Actions.UpdateName(text)) },
                    label = { Text(stringResource(id = com.ulises.common.resources.R.string.add_screen_edit_text_event_name_placeholder)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .clickable { isCalendarVisible = true }
                        .padding(12.dp)
                ) {
                    Icon(
                        painter = painterResource(id = com.ulises.common.resources.R.drawable.ic_calendar),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = uiState().dateTime.toHumanReadable(),
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                    )
                }
                Button(
                    onClick = { onActionPerformed(Actions.SendData) },
                    enabled = isDataReady,
                ) {
                    Text(
                        text = stringResource(id = com.ulises.common.resources.R.string.add_screen_button_save_date),
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
private fun PrevAddEventScreen() {
    CountdownAppTheme {
        AddEventScreen(
            uiState = {
                UiState(
                    isLoading = false,
                    dateTime = LocalDateTime.now(),
                )
            },
        )
    }
}