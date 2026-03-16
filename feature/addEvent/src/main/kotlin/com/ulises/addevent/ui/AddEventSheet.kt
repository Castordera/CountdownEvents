package com.ulises.addevent.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.domain.models.CountdownDate
import com.ulises.addevent.ui.components.DateField
import com.ulises.addevent.ui.components.DatePickerDialog
import com.ulises.addevent.ui.components.EmojiField
import com.ulises.addevent.ui.components.NameField
import com.ulises.theme.CountdownAppTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventSheet(
    onSaveEvent: (CountdownDate) -> Unit,
    onDismiss: () -> Unit,
) {
    val cs = MaterialTheme.colorScheme
    val availableEmojis = listOf("🐶", "🎂", "✈️", "💒", "🎉", "🎓", "🏖️", "💼", "🎵", "🏆", "❤️", "🎄", "🌎")
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var name by remember { mutableStateOf("") }
    var emoji by remember { mutableStateOf(availableEmojis[0]) }
    var date by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            initialDate = date,
            onSelectDate = {
                date = it
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false },
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 4.dp)
                    .width(36.dp)
                    .height(4.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.outlineVariant)
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Nuevo evento",
                style = MaterialTheme.typography.titleMedium,
                color = cs.onSurface,
                modifier = Modifier.padding(bottom = 2.dp),
            )
            HorizontalDivider(color = cs.outlineVariant, thickness = 0.5.dp)
            NameField(name) { name = it }
            EmojiField(selectedEmoji = emoji, availableEmojis = availableEmojis) { emoji = it }
            DateField(date) { showDatePicker = true }
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onSaveEvent(
                            CountdownDate(
                                id = "${Date().time}",
                                name = name,
                                createdAt = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                                date = date,
                                emoji = emoji,
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = cs.primary,
                    contentColor = cs.onPrimary,
                ),
                enabled = name.isNotBlank(),
            ) {
                Text(
                    text = "Guardar evento",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewAddEventSheet() {
    CountdownAppTheme {
        Surface {
            AddEventSheet(
                onSaveEvent = {},
                onDismiss = {},
            )
        }
    }
}