package com.example.modoguardian.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.modoguardian.R
import com.example.modoguardian.ui.theme.GuardianTheme
import com.example.modoguardian.viewmodel.CamaraUi
import com.example.modoguardian.viewmodel.EventoUi
import com.example.modoguardian.viewmodel.InicioUiState

/**
 * Herramienta disponible para el rol actual del usuario.
 */
data class RoleTool(
    val title: String,
    val description: String,
    val route: String
)

/**
 * Contenido principal de los dashboards (admin, supervisor y operador).
 */
@Composable
fun InicioContent(
    state: InicioUiState,
    tools: List<RoleTool>,
    onVerTodasCamaras: () -> Unit = {},
    onCamaraClick: (CamaraUi) -> Unit = {},
    onVerEventos: () -> Unit = {},
    onToolClick: (RoleTool) -> Unit = {}
) {
    GuardCard(contentPadding = 16.dp) {
        Text(
            text = stringResource(R.string.dashboard_general_status),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(14.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SummaryItem(
                modifier = Modifier.weight(1f),
                value = state.totalCamaras.toString(),
                label = stringResource(R.string.dashboard_total),
                color = MaterialTheme.colorScheme.primary
            )
            SummaryDivider()
            SummaryItem(
                modifier = Modifier.weight(1f),
                value = state.camarasActivas.toString(),
                label = stringResource(R.string.dashboard_active),
                color = GuardianTheme.colors.success
            )
            SummaryDivider()
            SummaryItem(
                modifier = Modifier.weight(1f),
                value = state.camarasAtencion.toString(),
                label = stringResource(R.string.dashboard_attention),
                color = GuardianTheme.colors.warning
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = stringResource(R.string.dashboard_last_sync),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    Spacer(Modifier.height(16.dp))

    SectionHeader(
        title = stringResource(R.string.dashboard_assigned_cameras),
        actionText = stringResource(R.string.ver_todas),
        onAction = onVerTodasCamaras
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        state.camaras.forEach { camara ->
            CameraCard(camara = camara, onClick = { onCamaraClick(camara) })
        }
    }

    Spacer(Modifier.height(16.dp))

    SectionHeader(
        title = stringResource(R.string.dashboard_live_events),
        actionText = stringResource(R.string.ver_todo),
        onAction = onVerEventos
    )

    GuardCard(contentPadding = 0.dp) {
        if (state.eventos.isEmpty()) {
            Text(
                text = stringResource(R.string.events_empty),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            state.eventos.forEachIndexed { index, evento ->
                EventRow(evento = evento)
                if (index < state.eventos.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = GuardianTheme.colors.border.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }

    Spacer(Modifier.height(16.dp))

    SectionHeader(title = stringResource(R.string.dashboard_role_tools))

    tools.forEach { tool ->
        HomeOptionCard(
            titulo = tool.title,
            descripcion = tool.description,
            onClick = { onToolClick(tool) }
        )
    }
}

@Composable
private fun SummaryItem(
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            color = color,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SummaryDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(38.dp)
            .background(GuardianTheme.colors.border.copy(alpha = 0.6f))
    )
}

@Composable
private fun SectionHeader(
    title: String,
    actionText: String? = null,
    onAction: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
        if (actionText != null) {
            TextButton(onClick = onAction) {
                Text(
                    text = actionText,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
private fun CameraCard(
    camara: CamaraUi,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(164.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, GuardianTheme.colors.border.copy(alpha = 0.7f))
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Videocam,
                    contentDescription = null,
                    tint = if (camara.enLinea) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    modifier = Modifier.size(30.dp)
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    StatusDot(
                        color = if (camara.enLinea) {
                            GuardianTheme.colors.success
                        } else {
                            GuardianTheme.colors.critical
                        },
                        size = 5.dp
                    )
                    Text(
                        text = if (camara.enLinea) {
                            stringResource(R.string.camera_live_hd)
                        } else {
                            stringResource(R.string.dashboard_offline)
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = camara.nombre,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = camara.zona,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun EventRow(evento: EventoUi) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SemanticIconBox(
            icon = if (evento.critico) Icons.Outlined.Warning else Icons.Outlined.CheckCircle,
            tint = if (evento.critico) GuardianTheme.colors.critical else GuardianTheme.colors.success,
            containerColor = if (evento.critico) {
                GuardianTheme.colors.criticalContainer
            } else {
                GuardianTheme.colors.successContainer
            },
            size = 36.dp,
            iconSize = 18.dp
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = evento.titulo,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = evento.descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = evento.hora,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(3.dp))
            if (evento.critico && !evento.confirmado) {
                StatusBadge(
                    text = stringResource(R.string.alert_critical_badge),
                    color = GuardianTheme.colors.critical,
                    containerColor = GuardianTheme.colors.criticalContainer
                )
            } else {
                StatusBadge(
                    text = stringResource(R.string.events_confirmed),
                    color = GuardianTheme.colors.success,
                    containerColor = GuardianTheme.colors.successContainer
                )
            }
        }
    }
}
