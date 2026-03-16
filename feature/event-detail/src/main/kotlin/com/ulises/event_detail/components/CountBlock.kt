package com.ulises.event_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.theme.CountdownAppTheme
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

@Composable
internal fun CountBlock(
    days: Int,
    absDias: Int,
    isPast: Boolean,
    accentColor: Color,
    eventDate: LocalDate,
) {
    val cs = MaterialTheme.colorScheme

    val yearStart = LocalDate.of(eventDate.year, 1, 1)
    val yearEnd = LocalDate.of(eventDate.year, 12, 31)
    val totalDays = ChronoUnit.DAYS.between(yearStart, yearEnd).toFloat()
    val daysPast = ChronoUnit.DAYS.between(yearStart, eventDate).toFloat()
    val pct = (daysPast / totalDays).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(cs.surface)
            .border(0.5.dp, cs.outlineVariant, MaterialTheme.shapes.medium)
            .padding(horizontal = 20.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = if (isPast) "Hace" else "Faltan",
            style = MaterialTheme.typography.labelLarge,
            color = cs.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "$absDias",
            fontSize = 80.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = (-3).sp,
            lineHeight = 80.sp,
            color = accentColor,
        )

        Text(
            text = "Días",
            style = MaterialTheme.typography.labelLarge,
            color = cs.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Barra de progreso
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .clip(MaterialTheme.shapes.large)
                .background(cs.outlineVariant),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(pct)
                    .fillMaxHeight()
                    .clip(MaterialTheme.shapes.large)
                    .background(accentColor),
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Ene",
                style = MaterialTheme.typography.labelSmall,
                color = cs.onSurfaceVariant,
            )
            Text(
                text = "${(pct * 100).roundToInt()}% del año",
                style = MaterialTheme.typography.labelSmall,
                color = cs.onSurfaceVariant,
            )
            Text(
                text = "Dic",
                style = MaterialTheme.typography.labelSmall,
                color = cs.onSurfaceVariant,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewCountBlock() {
    CountdownAppTheme {
        Surface {
            CountBlock(
                days = 12,
                absDias = 12,
                isPast = false,
                accentColor = MaterialTheme.colorScheme.primary,
                eventDate = LocalDate.now().plusDays(12),
            )
        }
    }
}