package com.example.modoguardian.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.modoguardian.R
import com.example.modoguardian.ui.components.GuardCard
import com.example.modoguardian.ui.components.GuardOutlinedButton
import com.example.modoguardian.ui.components.SemanticIconBox
import com.example.modoguardian.ui.components.StatusBadge
import com.example.modoguardian.ui.components.StatusDot
import com.example.modoguardian.ui.theme.GuardianTheme

private data class EventoFila(
    val titulo: String,
    val descripcion: String,
    val hora: String,
    val tipo: String,
    val critico: Boolean,
    val estado: String
)

@Composable
fun EventosScreen(navController: NavController) {
    val context = LocalContext.current
    var filtroSeleccionado by remember { mutableStateOf(0) }

    val filtros = listOf(
        stringResource(R.string.events_tab_all),
        stringResource(R.string.events_tab_alerts),
        stringResource(R.string.events_tab_events),
        stringResource(R.string.events_tab_access)
    )

    val eventos = listOf(        EventoFila(
            titulo = stringResource(R.string.alert_unauthorized_motion),
            descripcion = stringResource(R.string.alert_perimeter_camera),
            hora = "14:32",
            tipo = "Intrusión perimetral",
            critico = true,
            estado = stringResource(R.string.events_pending_badge)
        ),
        EventoFila(
            titulo = "Acceso autorizado registrado",
            descripcion = "Entrada Principal - Cámara 01",
            hora = "14:18",
            tipo = "Control de acceso",
            critico = false,
            estado = stringResource(R.string.events_authorized)
        ),
        EventoFila(
            titulo = "Cambio de turno",
            descripcion = "Panel central de monitoreo",
            hora = "14:00",
            tipo = "Operación",
            critico = false,
            estado = stringResource(R.string.events_confirmed)
        ),
        EventoFila(
            titulo = "Falsa alarma verificada",
            descripcion = "Patio de Bodega - Cámara 02",
            hora = "13:44",
            tipo = "Verificación",
            critico = false,
            estado = stringResource(R.string.events_false_alarm)
        )
    )

    val eventosVisibles = when (filtroSeleccionado) {
        1 -> eventos.filter { it.critico }
        2 -> eventos.filter { !it.critico }
        else -> eventos
    }
    val etiquetaPendiente = stringResource(R.string.events_pending_badge)
    val totalPendientes = eventos.count { it.critico && it.estado == etiquetaPendiente }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.volver),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Column {
                Text(
                    text = stringResource(R.string.events_history),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.events_current_shift),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        GuardCard(contentPadding = 16.dp) {
            Text(
                text = stringResource(R.string.events_today).uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                EventoResumen(
                    modifier = Modifier.weight(1f),
                    value = eventos.size.toString(),
                    label = stringResource(R.string.events_registered).replace("\n", " "),
                    color = MaterialTheme.colorScheme.primary
                )
                EventoResumen(
                    modifier = Modifier.weight(1f),
                    value = eventos.count { it.critico }.toString(),
                    label = stringResource(R.string.events_criticals),
                    color = GuardianTheme.colors.critical
                )
                EventoResumen(
                    modifier = Modifier.weight(1f),
                    value = totalPendientes.toString(),
                    label = stringResource(R.string.events_pending),
                    color = GuardianTheme.colors.warning
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filtros.forEachIndexed { index, filtro ->
                val activo = index == filtroSeleccionado
                Surface(
                    shape = RoundedCornerShape(50),
                    color = if (activo) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                    modifier = Modifier.clickable { filtroSeleccionado = index }
                ) {
                    Text(
                        text = filtro,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (activo) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        GuardCard(contentPadding = 0.dp) {
            if (eventosVisibles.isEmpty()) {
                Text(
                    text = stringResource(R.string.events_empty),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                eventosVisibles.forEachIndexed { index, evento ->
                    EventoRow(evento = evento)
                    if (index < eventosVisibles.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = GuardianTheme.colors.border.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        GuardOutlinedButton(
            text = stringResource(R.string.events_export),
            onClick = {
                Toast.makeText(
                    context,
                    context.getString(R.string.events_export_done),
                    Toast.LENGTH_SHORT
                ).show()
            },
            icon = Icons.Outlined.History
        )

        Spacer(Modifier.height(26.dp))
    }
}

@Composable
private fun EventoResumen(
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
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun EventoRow(evento: EventoFila) {
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
                text = evento.titulo.replace("\n", " "),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                StatusDot(
                    color = if (evento.critico) {
                        GuardianTheme.colors.critical
                    } else {
                        GuardianTheme.colors.success
                    },
                    size = 5.dp
                )
                Text(
                    text = evento.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = evento.tipo,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = evento.hora,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(3.dp))
            StatusBadge(
                text = evento.estado,
                color = if (evento.critico) {
                    GuardianTheme.colors.critical
                } else {
                    GuardianTheme.colors.success
                },
                containerColor = if (evento.critico) {
                    GuardianTheme.colors.criticalContainer
                } else {
                    GuardianTheme.colors.successContainer
                }
            )
        }
    }
}
