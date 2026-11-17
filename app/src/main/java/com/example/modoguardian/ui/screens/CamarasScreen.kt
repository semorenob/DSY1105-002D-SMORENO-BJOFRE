package com.example.modoguardian.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.modoguardian.R
import com.example.modoguardian.ui.components.GuardCard
import com.example.modoguardian.ui.components.SemanticIconBox
import com.example.modoguardian.ui.components.StatusBadge
import com.example.modoguardian.ui.components.StatusDot
import com.example.modoguardian.ui.theme.GuardianTheme

private data class CamaraListado(
    val id: String,
    val nombre: String,
    val zona: String,
    val enLinea: Boolean,
    val grabando: Boolean,
    val senal: String
)

private val CAMARAS_DEMO = listOf(
    CamaraListado("CAM-01", "Entrada Principal", "Perímetro Norte", true, true, "Excelente"),
    CamaraListado("CAM-02", "Patio de Bodega", "Zona Sur", true, true, "Excelente"),
    CamaraListado("CAM-03", "Acceso Vehicular", "Perímetro Sur", true, false, "Buena"),
    CamaraListado("CAM-04", "Perímetro Norte", "Sector B", false, false, "Sin señal")
)

@Composable
fun CamarasScreen(navController: NavController) {
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
                    text = stringResource(R.string.dashboard_cameras),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.dashboard_panel_zone, "Cámaras"),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        GuardCard(contentPadding = 0.dp) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.42f),
                                GuardianTheme.colors.border.copy(alpha = 0.8f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Videocam,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.85f),
                    modifier = Modifier.size(56.dp)
                )
                StatusBadge(
                    text = stringResource(R.string.camera_live_hd),
                    color = GuardianTheme.colors.critical,
                    containerColor = GuardianTheme.colors.criticalContainer,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                )
                Text(
                    text = stringResource(R.string.camera_timestamp),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.camera_entrada_principal),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(R.string.camera_frame_info),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        GuardCard(contentPadding = 0.dp) {
            Text(
                text = stringResource(R.string.camera_device_status),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = GuardianTheme.colors.border.copy(alpha = 0.5f)
            )
            InfoRow(
                label = stringResource(R.string.camera_status),
                value = stringResource(R.string.camera_live)
            )
            InfoRow(
                label = stringResource(R.string.camera_signal),
                value = stringResource(R.string.camera_excellent)
            )
            InfoRow(
                label = stringResource(R.string.camera_recording),
                value = stringResource(R.string.camera_cloud_local)
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.dashboard_assigned_cameras),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(8.dp))

        CAMARAS_DEMO.forEach { camara ->
            GuardCard(
                modifier = Modifier.padding(bottom = 8.dp),
                contentPadding = 14.dp
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SemanticIconBox(
                        icon = if (camara.enLinea) Icons.Outlined.Videocam else Icons.Outlined.Warning,
                        tint = if (camara.enLinea) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            GuardianTheme.colors.critical
                        },
                        containerColor = if (camara.enLinea) {
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                        } else {
                            GuardianTheme.colors.criticalContainer
                        },
                        size = 38.dp,
                        iconSize = 19.dp
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp)
                    ) {
                        Text(
                            text = camara.nombre,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
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
                                text = camara.zona,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    StatusBadge(
                        text = if (camara.enLinea) {
                            stringResource(R.string.camera_live)
                        } else {
                            stringResource(R.string.dashboard_offline)
                        },
                        color = if (camara.enLinea) {
                            GuardianTheme.colors.success
                        } else {
                            GuardianTheme.colors.critical
                        },
                        containerColor = if (camara.enLinea) {
                            GuardianTheme.colors.successContainer
                        } else {
                            GuardianTheme.colors.criticalContainer
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(26.dp))
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
    }
}
