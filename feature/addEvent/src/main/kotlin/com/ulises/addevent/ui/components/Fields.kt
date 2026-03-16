package com.ulises.addevent.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.date_utils.format
import java.time.LocalDate

@Composable
internal fun NameField(
    value: String,
    onValueChange: (String) -> Unit
) {
    val cs = MaterialTheme.colorScheme

    Column {
        LabelField("Nombre")
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = "Ej: Cumpleaños, viaje, boda…",
                    style = MaterialTheme.typography.bodySmall,
                    color = cs.onSurfaceVariant,
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = cs.background,
                focusedContainerColor = cs.background,
                unfocusedBorderColor = cs.outlineVariant,
                focusedBorderColor = cs.outline,
            ),
            textStyle = MaterialTheme.typography.titleMedium.copy(color = cs.onSurface),
        )
    }
}

@Composable
internal fun EmojiField(
    selectedEmoji: String,
    availableEmojis: List<String>,
    onEmojiClick: (String) -> Unit
) {
    val cs = MaterialTheme.colorScheme

    Column {
        LabelField("Ícono")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(availableEmojis) { emoji ->
                val isSelected = emoji == selectedEmoji
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(if (isSelected) cs.outlineVariant else cs.background)
                        .border(
                            width = if (isSelected) 1.dp else 0.5.dp,
                            color = if (isSelected) cs.onSurface else cs.outlineVariant,
                            shape = MaterialTheme.shapes.small,
                        )
                        .clickable { onEmojiClick(emoji) },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = emoji, fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
internal fun DateField(
    date: LocalDate,
    onClick: () -> Unit
) {
    val cs = MaterialTheme.colorScheme

    Column {
        LabelField("Fecha")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(cs.background)
                .border(0.5.dp, cs.outlineVariant, MaterialTheme.shapes.medium)
                .clickable { onClick() }
                .padding(horizontal = 14.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = cs.onSurfaceVariant,
                modifier = Modifier.size(16.dp),
            )
            Text(
                text = date.format("d 'de' MMMM 'de' yyyy"),
                style = MaterialTheme.typography.titleMedium,
                color = cs.onSurface,
            )
        }
    }
}