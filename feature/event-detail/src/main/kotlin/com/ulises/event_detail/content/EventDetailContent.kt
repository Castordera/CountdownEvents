package com.ulises.event_detail.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.components.backgroundColor
import com.ulises.components.textColor
import com.ulises.components.toolbars.AppTopBar
import com.ulises.components.toolbars.AppTopBarIcon
import com.ulises.date_utils.format
import com.ulises.date_utils.fromToday
import com.ulises.event_detail.Action
import com.ulises.event_detail.components.CountBlock
import com.ulises.event_detail.components.StatCard
import com.ulises.event_detail.models.DetailUiState
import com.ulises.preview_data.getMockCountDown
import com.ulises.theme.CountdownAppTheme
import kotlin.math.abs

@Composable
internal fun EventDetailContent(
    uiState: DetailUiState,
    onHandleAction: (Action) -> Unit = {},
) {
    Scaffold(
        topBar = {
            AppTopBar(
                onBackPress = { onHandleAction(Action.BackPressed) },
                actions = {
                    AppTopBarIcon({}) {
                        Icon(Icons.Filled.DateRange, null)
                    }
                    AppTopBarIcon({}) {
                        Icon(Icons.Filled.Edit, null)
                    }
                }
            )
        }
    ) { innerPadding ->
        //  In case is empty
        if (uiState.countdownDate == null) return@Scaffold
        val days = uiState.countdownDate.date.fromToday()
        val absDias = abs(days)
        val isPast = days < 0

        val cs = MaterialTheme.colorScheme

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(backgroundColor(days)),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = uiState.countdownDate.emoji, fontSize = 26.sp)
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = uiState.countdownDate.name,
                style = MaterialTheme.typography.headlineLarge,
                color = cs.onSurface,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = uiState.countdownDate.date.format("EEEE, d 'de' MMMM 'de' yyyy"),
                style = MaterialTheme.typography.bodySmall,
                color = cs.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(28.dp))
            CountBlock(
                days = days,
                absDias = absDias,
                isPast = isPast,
                accentColor = textColor(days),
                eventDate = uiState.countdownDate.date,
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard(
                    label = "Semanas",
                    valor = "${absDias / 7}",
                    sub = if (isPast) "transcurridas" else "restantes",
                    modifier = Modifier.weight(1f),
                )
                StatCard(
                    label = "Meses",
                    valor = "${absDias / 30}",
                    sub = if (isPast) "transcurridos" else "restantes",
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Creado: ${uiState.countdownDate.createdAt.format("d MMM yyyy")}",
                    style = MaterialTheme.typography.labelSmall,
                    color = cs.onSurfaceVariant,
                )
                Text(
                    text = "ID: ${uiState.countdownDate.id}",
                    style = MaterialTheme.typography.labelSmall,
                    color = cs.onSurfaceVariant,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun EventDetailContentPreview() {
    CountdownAppTheme {
        EventDetailContent(
            uiState =
                DetailUiState(
                    countdownDate = getMockCountDown(
                        name = "This is a huge value to have as name",
                        realDate = "2023-12-08T"
                    ),
                )
        )
    }
}