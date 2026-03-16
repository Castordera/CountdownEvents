package com.ulises.list.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.CountdownDate
import com.ulises.components.backgroundColor
import com.ulises.components.textColor
import com.ulises.date_utils.format
import com.ulises.date_utils.fromToday
import com.ulises.preview_data.listItemsPreview
import com.ulises.theme.CountdownAppTheme
import kotlin.math.abs

@Composable
internal fun EventCard(
    event: CountdownDate,
    onClick: () -> Unit,
) {
    val cs = MaterialTheme.colorScheme
    val days = event.date.fromToday()
    val absDias = abs(days)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(cs.surface)
            .border(0.5.dp, cs.outlineVariant, MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(13.dp),
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(MaterialTheme.shapes.small)
                .background(backgroundColor(days)),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = event.emoji, fontSize = 20.sp)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = event.name,
                style = MaterialTheme.typography.titleMedium,
                color = cs.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = event.date.format(),
                style = MaterialTheme.typography.bodySmall,
                color = cs.onSurfaceVariant,
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$absDias",
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = (-1).sp,
                lineHeight = 30.sp,
                color = textColor(days),
            )
            Text(
                text = if (days == 0) "HOY" else "DÍAS",
                style = MaterialTheme.typography.labelSmall,
                color = cs.onSurfaceVariant,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewEventCard() {
    CountdownAppTheme {
        Surface {
            Column {
                listItemsPreview.forEach {
                    EventCard(
                        event = it,
                        onClick = {}
                    )
                }
            }
        }
    }
}