package com.example.countdownapp.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ulises.theme.CountdownAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDeleteDialog() {
    AlertDialog(
        onDismissRequest = { /*TODO*/ }
    ) {
        Text(text = "This is a demo")
    }
}

@Preview
@Composable
fun PrevConfirmDeleteDialog() {
    CountdownAppTheme {
        ConfirmDeleteDialog()
    }
}