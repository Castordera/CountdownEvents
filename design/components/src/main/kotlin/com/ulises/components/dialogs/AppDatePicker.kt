@file:OptIn(ExperimentalMaterial3Api::class)

package com.ulises.components.dialogs

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ulises.components.R
import com.ulises.theme.CountdownAppTheme

@Composable
fun AppDatePicker(
    datePickerState: DatePickerState = rememberDatePickerState(),
    isVisible: Boolean,
    onCancelClick: () -> Unit,
    onDateSelected: (Long?) -> Unit
) {
    if (!isVisible) return
    DatePickerDialog(
        onDismissRequest = onCancelClick,
        confirmButton = {
            TextButton(
                onClick = { onDateSelected(datePickerState.selectedDateMillis) }
            ) {
                Text(text = stringResource(id = R.string.dialog_button_positive))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancelClick
            ) {
                Text(text = stringResource(id = R.string.dialog_button_cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Preview
@Composable
private fun PrevAppDatePicker() {
    CountdownAppTheme {
        AppDatePicker(
            isVisible = true,
            onCancelClick = {},
            onDateSelected = {}
        )
    }
}