package com.ulises.event_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.theme.CountdownAppTheme

@Composable
internal fun StatCard(
    label: String,
    valor: String,
    sub: String,
    modifier: Modifier = Modifier,
) {
    val cs = MaterialTheme.colorScheme

    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(cs.surface)
            .border(0.5.dp, cs.outlineVariant, MaterialTheme.shapes.medium)
            .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = cs.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = valor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = cs.onSurface,
        )
        Spacer(modifier = Modifier.height(1.dp))
        Text(
            text = sub,
            style = MaterialTheme.typography.bodySmall,
            color = cs.onSurfaceVariant,
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewStatCard() {
    CountdownAppTheme {
        Surface {
            StatCard(
                label = "Label",
                valor = "Valor",
                sub = "Sub",
            )
        }
    }
}