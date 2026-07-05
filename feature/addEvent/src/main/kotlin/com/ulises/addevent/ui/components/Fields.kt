package com.ulises.addevent.ui.components

import androidx.compose.animation.animateContentSize
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
import com.ulises.theme.colors
import com.ulises.theme.shapes
import com.ulises.theme.typography
import java.time.LocalDate

@Composable
internal fun NameField(
    value: String,
    onValueChange: (String) -> Unit
) {
    Column {
        LabelField("Nombre")
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = "Ej: Cumpleaños, viaje, boda…",
                    style = typography.bodySmall,
                    color = colors.onSurfaceVariant,
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colors.background,
                focusedContainerColor = colors.background,
                unfocusedBorderColor = colors.outlineVariant,
                focusedBorderColor = colors.outline,
            ),
            textStyle = typography.titleMedium.copy(color = colors.onSurface),
        )
    }
}

@Composable
internal fun EmojiField(
    selectedEmoji: String,
    availableEmojis: List<String>,
    onEmojiClick: (String) -> Unit
) {
    Column {
        LabelField("Ícono")
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(availableEmojis) { emoji ->
                val isSelected = emoji == selectedEmoji
                Box(
                    modifier = Modifier
                        .size(if (isSelected) 45.dp else 40.dp)
                        .animateContentSize()
                        .clip(shapes.small)
                        .background(if (isSelected) colors.outlineVariant else colors.background)
                        .border(
                            width = if (isSelected) 1.dp else 0.5.dp,
                            color = if (isSelected) colors.onSurface else colors.outlineVariant,
                            shape = shapes.small,
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
    Column {
        LabelField("Fecha")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shapes.medium)
                .background(colors.background)
                .border(0.5.dp, colors.outlineVariant, shapes.medium)
                .clickable { onClick() }
                .padding(horizontal = 14.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = colors.onSurfaceVariant,
                modifier = Modifier.size(16.dp),
            )
            Text(
                text = date.format("d 'de' MMMM 'de' yyyy"),
                style = typography.titleMedium,
                color = colors.onSurface,
            )
        }
    }
}