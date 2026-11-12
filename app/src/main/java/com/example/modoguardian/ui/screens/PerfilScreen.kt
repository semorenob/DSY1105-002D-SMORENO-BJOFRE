package com.example.modoguardian.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.PowerSettingsNew
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.modoguardian.R
import com.example.modoguardian.ui.components.GuardCard
import com.example.modoguardian.ui.components.GuardOutlinedButton
import com.example.modoguardian.ui.components.GuardToggleRow
import com.example.modoguardian.ui.components.IntelligentImage
import com.example.modoguardian.ui.components.ProfileRow
import com.example.modoguardian.ui.components.SemanticIconBox
import com.example.modoguardian.ui.components.StatusBadge
import com.example.modoguardian.ui.theme.GuardianTheme
import com.example.modoguardian.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    viewModel: PerfilViewModel = viewModel(),
    isDarkTheme: Boolean = true,
    onToggleDarkTheme: (Boolean) -> Unit = {},
    onCerrarSesion: () -> Unit = {},
    onVerCamaras: () -> Unit = {},
    onVerEventos: () -> Unit = {}
) {
    val context = LocalContext.current
    val imageUri by viewModel.imageUri.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }
    var mostrarSelectorFoto by remember { mutableStateOf(false) }
    var mostrarCerrarSesion by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) viewModel.setImageUri(uri)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success && tempCameraUri != null) {
            viewModel.setImageUri(tempCameraUri)
        }
    }

    fun abrirCamara() {
        val uri = viewModel.createTempImageUri(context)
        tempCameraUri = uri
        cameraLauncher.launch(uri)
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) abrirCamara()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.profile_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.profile_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable {
                        Toast.makeText(
                            context,
                            context.getString(R.string.profile_settings),
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = stringResource(R.string.profile_settings),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        GuardCard(contentPadding = 16.dp) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box {
                    IntelligentImage(
                        imageUri = imageUri,
                        modifier = Modifier.size(72.dp),
                        fallbackText = "VP"
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable { mostrarSelectorFoto = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PhotoCamera,
                            contentDescription = stringResource(R.string.profile_change_photo),
                            tint = Color.White,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 14.dp)
                ) {
                    Text(
                        text = stringResource(R.string.profile_name),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(R.string.profile_email),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(7.dp))
                    StatusBadge(
                        text = stringResource(R.string.profile_verified),
                        color = GuardianTheme.colors.success,
                        containerColor = GuardianTheme.colors.successContainer,
                        icon = Icons.Outlined.CheckCircle
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoChip(
                    icon = Icons.Outlined.LocationOn,
                    text = stringResource(R.string.profile_zone),
                    color = MaterialTheme.colorScheme.primary,
                    container = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                )
                InfoChip(
                    icon = Icons.Outlined.CheckCircle,
                    text = stringResource(R.string.profile_shift_active),
                    color = GuardianTheme.colors.success,
                    container = GuardianTheme.colors.successContainer
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        GuardCard(contentPadding = 0.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileStat(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Schedule,
                    value = "14:00 - 22:00",
                    label = stringResource(R.string.profile_shift),
                    tint = MaterialTheme.colorScheme.primary
                )
                StatDivider()
                ProfileStat(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Videocam,
                    value = "12 asignadas",
                    label = stringResource(R.string.profile_cameras),
                    tint = MaterialTheme.colorScheme.primary
                )
                StatDivider()
                ProfileStat(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Warning,
                    value = "3 resueltas",
                    label = stringResource(R.string.profile_alerts),
                    tint = GuardianTheme.colors.warning
                )
            }
        }

        Spacer(Modifier.height(20.dp))
        MenuSection(stringResource(R.string.profile_section_account)) {
            ProfileRow(
                icon = Icons.Outlined.Videocam,
                title = stringResource(R.string.profile_my_cameras),
                onClick = onVerCamaras,
                trailing = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CountBadge("12")
                        Spacer(Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            )
            RowDivider()
            ProfileRow(
                icon = Icons.Outlined.History,
                title = stringResource(R.string.profile_shift_history),
                subtitle = stringResource(R.string.profile_shift_history_desc),
                onClick = onVerEventos
            )
            RowDivider()
            ProfileRow(
                icon = Icons.Outlined.Badge,
                title = stringResource(R.string.profile_certificate),
                subtitle = stringResource(R.string.profile_certificate_desc),
                onClick = { }
            )
        }

        Spacer(Modifier.height(18.dp))
        MenuSection(stringResource(R.string.profile_section_security)) {
            ProfileRow(
                icon = Icons.Outlined.VpnKey,
                title = stringResource(R.string.profile_change_password),
                onClick = { }
            )
            RowDivider()
            ProfileRow(
                icon = Icons.Outlined.Devices,
                title = stringResource(R.string.profile_active_sessions),
                subtitle = stringResource(R.string.profile_active_sessions_desc),
                onClick = { },
                trailing = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CountBadge(stringResource(R.string.profile_one_device))
                        Spacer(Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            )
            RowDivider()
            GuardToggleRow(
                icon = Icons.Outlined.VerifiedUser,
                title = stringResource(R.string.profile_two_factor),
                subtitle = stringResource(R.string.profile_two_factor_desc),
                checked = uiState.twoFactor,
                onCheckedChange = viewModel::onTwoFactorChange
            )
            RowDivider()
            GuardToggleRow(
                icon = Icons.Outlined.NotificationsActive,
                title = stringResource(R.string.profile_push),
                subtitle = stringResource(R.string.profile_push_desc),
                checked = uiState.pushNotifications,
                onCheckedChange = viewModel::onPushNotificationsChange
            )
            RowDivider()
            GuardToggleRow(
                icon = Icons.Outlined.Warning,
                title = stringResource(R.string.profile_critical_alerts),
                subtitle = stringResource(R.string.profile_critical_alerts_desc),
                checked = uiState.criticalAlerts,
                onCheckedChange = viewModel::onCriticalAlertsChange
            )
        }

        Spacer(Modifier.height(18.dp))
        MenuSection(stringResource(R.string.profile_section_app)) {
            GuardToggleRow(
                icon = Icons.Outlined.DarkMode,
                title = stringResource(R.string.profile_dark_mode),
                subtitle = if (isDarkTheme) stringResource(R.string.profile_dark_mode_desc)
                else stringResource(R.string.profile_dark_mode_desc_off),
                checked = isDarkTheme,
                onCheckedChange = onToggleDarkTheme
            )
            RowDivider()
            ProfileRow(
                icon = Icons.Outlined.SupportAgent,
                title = stringResource(R.string.profile_help),
                subtitle = stringResource(R.string.profile_help_desc),
                onClick = { }
            )
            RowDivider()
            ProfileRow(
                icon = Icons.Outlined.Description,
                title = stringResource(R.string.profile_terms),
                onClick = { }
            )
        }

        Spacer(Modifier.height(22.dp))

        GuardOutlinedButton(
            text = stringResource(R.string.profile_logout),
            onClick = { mostrarCerrarSesion = true },
            borderColor = GuardianTheme.colors.critical.copy(alpha = 0.55f),
            contentColor = GuardianTheme.colors.critical,
            icon = Icons.Outlined.PowerSettingsNew
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.profile_version),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = stringResource(R.string.profile_local_preview),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(26.dp))
    }

    if (mostrarSelectorFoto) {
        AlertDialog(
            onDismissRequest = { mostrarSelectorFoto = false },
            title = { Text(stringResource(R.string.profile_photo_title)) },
            text = { Text(stringResource(R.string.profile_change_photo)) },
            confirmButton = {
                TextButton(onClick = {
                    mostrarSelectorFoto = false
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        abrirCamara()
                    } else {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }) {
                    Text(stringResource(R.string.profile_photo_camera))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    mostrarSelectorFoto = false
                    galleryLauncher.launch("image/*")
                }) {
                    Text(stringResource(R.string.profile_photo_gallery))
                }
            }
        )
    }

    if (mostrarCerrarSesion) {
        AlertDialog(
            onDismissRequest = { mostrarCerrarSesion = false },
            title = { Text(stringResource(R.string.profile_logout_title)) },
            text = { Text(stringResource(R.string.profile_logout_message)) },
            confirmButton = {
                TextButton(onClick = {
                    mostrarCerrarSesion = false
                    onCerrarSesion()
                }) {
                    Text(
                        text = stringResource(R.string.profile_logout),
                        color = GuardianTheme.colors.critical
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarCerrarSesion = false }) {
                    Text(stringResource(R.string.cancelar))
                }
            }
        )
    }
}

@Composable
private fun MenuSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    GuardCard(contentPadding = 0.dp) {
        content()
    }
}

@Composable
private fun RowDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = GuardianTheme.colors.border.copy(alpha = 0.5f)
    )
}

@Composable
private fun InfoChip(
    icon: ImageVector,
    text: String,
    color: Color,
    container: Color
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = container
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(13.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = color
            )
        }
    }
}

@Composable
private fun ProfileStat(
    icon: ImageVector,
    value: String,
    label: String,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(17.dp))
        Spacer(Modifier.height(5.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun StatDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(44.dp)
            .background(GuardianTheme.colors.border.copy(alpha = 0.6f))
    )
}

@Composable
private fun CountBadge(text: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
