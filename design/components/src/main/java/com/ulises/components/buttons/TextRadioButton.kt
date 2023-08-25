package com.ulises.components.buttons

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulises.theme.CountdownAppTheme

@Composable
fun TextRadioButton(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean = false,
    onClick: (String) -> Unit = {}
) {
    Row(
        modifier = modifier.selectable(
            selected = selected,
            onClick = { onClick(text) },
            role = Role.RadioButton
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = text)
        RadioButton(
            selected = selected,
            onClick = null
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevTextRadioButton() {
    CountdownAppTheme {
        Surface {
            Column {
                TextRadioButton(
                    text = "Demo",
                    selected = false
                )
                TextRadioButton(
                    text = "Demo",
                    selected = true
                )
            }
        }
    }
}