package com.example.modoguardian.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.modoguardian.R
import com.example.modoguardian.navigation.Screen
import com.example.modoguardian.ui.components.GuardCard
import com.example.modoguardian.ui.components.GuardPrimaryButton
import com.example.modoguardian.ui.components.GuardTextField
import com.example.modoguardian.ui.components.GuardToggleRow
import com.example.modoguardian.ui.components.IntelligentImage
import com.example.modoguardian.ui.components.PremiumCard
import com.example.modoguardian.ui.theme.GuardianTheme
import com.example.modoguardian.viewmodel.UsuarioViewModel
import java.io.File

@Composable
fun RegistroIntegralScreen(
    navController: NavController,
    viewModel: UsuarioViewModel,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val errores by viewModel.errores.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val mensajeExito by viewModel.mensajeExito.collectAsState()

    var tempCameraUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.actualizarFoto(it) }
    }

    fun crearTempUri(): Uri {
        val imagesDir = File(context.filesDir, "images").apply { mkdirs() }
        val file = File.createTempFile("photo_", ".jpg", imagesDir)
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito ->
        if (exito && tempCameraUri != null) {
            viewModel.actualizarFoto(tempCameraUri)
        }
    }

    fun abrirCamara() {
        val uri = crearTempUri()
        tempCameraUri = uri
        cameraLauncher.launch(uri)
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) abrirCamara()
    }

    if (cargando) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

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
                    text = stringResource(R.string.register_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.register_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        GuardCard(contentPadding = 16.dp) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IntelligentImage(
                    imageUri = uiState.fotoUri,
                    modifier = Modifier.size(74.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 14.dp)
                ) {
                    Text(
                        text = stringResource(R.string.register_photo),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(R.string.register_photo_hint),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        PhotoButton(
                            text = stringResource(R.string.register_camera),
                            icon = Icons.Outlined.PhotoCamera,
                            onClick = {
                                if (ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.CAMERA
                                    ) == PackageManager.PERMISSION_GRANTED
                                ) {
                                    abrirCamara()
                                } else {
                                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            }
                        )
                        PhotoButton(
                            text = stringResource(R.string.register_gallery),
                            icon = Icons.Outlined.Image,
                            onClick = { galleryLauncher.launch("image/*") }
                        )
                    }
                }
            }
            errores.fotoError?.let {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = GuardianTheme.colors.critical
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        GuardCard(contentPadding = 16.dp) {
            GuardTextField(
                value = uiState.nombre,
                onValueChange = viewModel::onNombreChange,
                label = stringResource(R.string.register_name),
                leadingIcon = Icons.Outlined.Person,
                isError = errores.nombreError != null,
                errorText = errores.nombreError
            )
            Spacer(Modifier.height(12.dp))
            GuardTextField(
                value = uiState.correo,
                onValueChange = viewModel::onCorreoChange,
                label = stringResource(R.string.register_email),
                leadingIcon = Icons.Outlined.Email,
                isError = errores.correoError != null,
                errorText = errores.correoError
            )
            Spacer(Modifier.height(12.dp))
            GuardTextField(
                value = uiState.clave,
                onValueChange = viewModel::onClaveChange,
                label = stringResource(R.string.register_password),
                leadingIcon = Icons.Outlined.Lock,
                isError = errores.claveError != null,
                errorText = errores.claveError,
                visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
            )
            Spacer(Modifier.height(12.dp))
            GuardTextField(
                value = uiState.direccion,
                onValueChange = viewModel::onDireccionChange,
                label = stringResource(R.string.register_address),
                leadingIcon = Icons.Outlined.LocationOn,
                isError = errores.direccionError != null,
                errorText = errores.direccionError
            )

            Spacer(Modifier.height(14.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.aceptaTerminos,
                    onCheckedChange = viewModel::onTerminosChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        checkmarkColor = Color.White,
                        uncheckedColor = GuardianTheme.colors.border
                    )
                )
                Text(
                    text = stringResource(R.string.register_terms_label),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            errores.terminosError?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = GuardianTheme.colors.critical
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        GuardCard(contentPadding = 0.dp) {
            GuardToggleRow(
                icon = Icons.Outlined.Star,
                title = stringResource(R.string.register_premium),
                subtitle = stringResource(R.string.register_premium_desc),
                checked = uiState.modoEspecial,
                onCheckedChange = { viewModel.toggleModoEspecial() }
            )
        }

        Spacer(Modifier.height(10.dp))
        PremiumCard(modoEspecial = uiState.modoEspecial)

        AnimatedVisibility(visible = mensajeExito) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = GuardianTheme.colors.successContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = GuardianTheme.colors.success,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.size(8.dp))
                    Text(
                        text = stringResource(R.string.register_preference_updated),
                        style = MaterialTheme.typography.bodySmall,
                        color = GuardianTheme.colors.success
                    )
                }
            }
        }

        Spacer(Modifier.height(18.dp))

        GuardPrimaryButton(
            text = stringResource(R.string.register_submit),
            onClick = {
                viewModel.guardarEnRoom { navController.navigate(Screen.ResumenScreen.route) }
            }
        )

        Spacer(Modifier.height(26.dp))
    }
}

@Composable
private fun PhotoButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .size(width = 108.dp, height = 36.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(15.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
