package com.example.modoguardian.ui.screens

import android.widget.Toast
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
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material.icons.outlined.Videocam
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.modoguardian.R
import com.example.modoguardian.ui.components.GuardCard
import com.example.modoguardian.ui.components.GuardOutlinedButton
import com.example.modoguardian.ui.components.GuardPrimaryButton
import com.example.modoguardian.ui.components.ProfileRow
import com.example.modoguardian.ui.components.SemanticIconBox
import com.example.modoguardian.ui.components.StatusBadge
import com.example.modoguardian.ui.theme.GuardianTheme

@Composable
fun AlertaCriticaScreen(navController: NavController) {
    val context = LocalContext.current
    var respuesta by remember { mutableStateOf<String?>(null) }

    val mensajeConfirmada = stringResource(R.string.alert_confirmed_message)
    val mensajeFalsaAlarma = stringResource(R.string.alert_dismissed_message)
    val mensajeLlamada = stringResource(R.string.alert_call_message)

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
                    text = stringResource(R.string.alert_critical),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.alert_ago),
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
                    .height(150.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                GuardianTheme.colors.critical.copy(alpha = 0.55f),
                                GuardianTheme.colors.criticalContainer
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Warning,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
                StatusBadge(
                    text = stringResource(R.string.alert_critical_badge),
                    color = GuardianTheme.colors.critical,
                    containerColor = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                )
                Text(
                    text = stringResource(R.string.alert_clip_label),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                )
                Text(
                    text = stringResource(R.string.alert_camera_short),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.alert_unauthorized_motion),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Videocam,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = stringResource(R.string.alert_perimeter_camera),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.alert_datetime),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = stringResource(R.string.alert_ai_confidence),
                    style = MaterialTheme.typography.bodySmall,
                    color = GuardianTheme.colors.warning
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        GuardCard(contentPadding = 0.dp) {
            Text(
                text = stringResource(R.string.alert_event_details),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = GuardianTheme.colors.border.copy(alpha = 0.5f)
            )
            DetalleRow(
                label = stringResource(R.string.alert_type),
                value = stringResource(R.string.alert_type_value)
            )
            DetalleRow(
                label = stringResource(R.string.alert_zone),
                value = stringResource(R.string.alert_zone_value)
            )
            DetalleRow(
                label = stringResource(R.string.alert_camera),
                value = stringResource(R.string.alert_camera_short)
            )
            DetalleRow(
                label = stringResource(R.string.alert_duration),
                value = stringResource(R.string.alert_duration_value)
            )
        }

        Spacer(Modifier.height(14.dp))

        GuardCard(contentPadding = 16.dp) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SemanticIconBox(
                    icon = Icons.Outlined.Warning,
                    tint = GuardianTheme.colors.warning,
                    containerColor = GuardianTheme.colors.warningContainer,
                    size = 34.dp,
                    iconSize = 17.dp
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.alert_response_time),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = stringResource(R.string.alert_timer_start),
                        style = MaterialTheme.typography.titleLarge,
                        color = GuardianTheme.colors.critical,
                        fontWeight = FontWeight.Bold
                    )
                }
                StatusBadge(
                    text = stringResource(R.string.alert_auto_escalated),
                    color = GuardianTheme.colors.warning,
                    containerColor = GuardianTheme.colors.warningContainer
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.alert_protocol_note),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.alert_required_actions),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(8.dp))

        GuardPrimaryButton(
            text = stringResource(R.string.alert_confirm),
            onClick = {
                respuesta = mensajeConfirmada
                Toast.makeText(context, mensajeConfirmada, Toast.LENGTH_SHORT).show()
            },
            icon = Icons.Outlined.CheckCircle
        )

        Spacer(Modifier.height(8.dp))

        GuardOutlinedButton(
            text = stringResource(R.string.alert_false_alarm),
            onClick = {
                respuesta = mensajeFalsaAlarma
                Toast.makeText(context, mensajeFalsaAlarma, Toast.LENGTH_SHORT).show()
            },
            borderColor = GuardianTheme.colors.critical.copy(alpha = 0.55f),
            contentColor = GuardianTheme.colors.critical,
            icon = Icons.Outlined.Warning
        )

        Spacer(Modifier.height(12.dp))

        GuardCard(contentPadding = 0.dp) {
            ProfileRow(
                icon = Icons.Outlined.PhotoCamera,
                title = stringResource(R.string.alert_report_photo),
                onClick = {
                    Toast.makeText(
                        context,
                        context.getString(R.string.alert_report_photo),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = GuardianTheme.colors.border.copy(alpha = 0.5f)
            )
            ProfileRow(
                icon = Icons.Outlined.SupportAgent,
                title = stringResource(R.string.alert_call_center),
                onClick = {
                    respuesta = mensajeLlamada
                    Toast.makeText(context, mensajeLlamada, Toast.LENGTH_SHORT).show()
                }
            )
        }

        respuesta?.let { mensaje ->
            Spacer(Modifier.height(14.dp))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = GuardianTheme.colors.successContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = mensaje,
                    style = MaterialTheme.typography.bodySmall,
                    color = GuardianTheme.colors.success,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        Spacer(Modifier.height(26.dp))
    }
}

@Composable
private fun DetalleRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value.replace("\n", " "),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
    }
}
