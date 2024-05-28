package com.ulises.components.dialogs

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulises.theme.CountdownAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleAlertDialog(
    isVisible: Boolean,
    title: String,
    message: String,
    positiveTextButton: String? = null,
    positiveClickButton: (() -> Unit)? = null,
    negativeTextButton: String? = null,
    negativeClickButton: (() -> Unit)? = null,
    onDismissDialog: () -> Unit
) {
    if (!isVisible) return
    BasicAlertDialog(
        onDismissRequest = onDismissDialog
    ) {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = message)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (positiveTextButton != null && positiveClickButton != null) {
                        TextButton(onClick = positiveClickButton) {
                            Text(text = positiveTextButton)
                        }
                    }
                    if (negativeTextButton != null && negativeClickButton != null) {
                        TextButton(onClick = negativeClickButton) {
                            Text(
                                text = negativeTextButton,
                                color = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrevSimpleAlertDialog() {
    CountdownAppTheme {
        SimpleAlertDialog(
            isVisible = true,
            title = "This is my demo title",
            message = "Message to test this thing to be applied",
            positiveTextButton = "Accept",
            positiveClickButton = {},
            negativeTextButton = "Cancel",
            negativeClickButton = {},
            onDismissDialog = {}
        )
    }
}