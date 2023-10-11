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
import androidx.compose.ui.tooling.preview.Preview
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
                Text(text = "Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancelClick
            ) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Preview
@Composable
fun PrevAppDatePicker() {
    CountdownAppTheme {
        AppDatePicker(
            isVisible = true,
            onCancelClick = {},
            onDateSelected = {}
        )
    }
}