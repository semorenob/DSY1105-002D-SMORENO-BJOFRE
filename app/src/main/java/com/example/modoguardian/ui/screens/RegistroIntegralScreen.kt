package com.example.modoguardian.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.modoguardian.ui.components.IntelligentImage
import com.example.modoguardian.viewmodel.UsuarioViewModel
import java.io.File

@Composable
fun RegistroIntegralScreen(
    viewModel: UsuarioViewModel,
    onNavegarResumen: () -> Unit
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

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito ->
        if (exito && tempCameraUri != null) {
            viewModel.actualizarFoto(tempCameraUri)
        }
    }

    fun crearTempUri(): Uri {
        val file = File.createTempFile("photo_", ".jpg", context.getExternalFilesDir(null))
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }

    val colorBotonAnimado by animateColorAsState(
        targetValue = if (uiState.modoEspecial) Color(0xFF2E7D32) else Color.Gray,
        label = "colorBoton"
    )

    val textoBotonDerivado by remember {
        derivedStateOf { if (uiState.modoEspecial) "Desactivar modo Premium" else "Activar modo Premium" }
    }

    if (cargando) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IntelligentImage(imageUri = uiState.fotoUri)
            errores.fotoError?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            Row(modifier = Modifier.padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    val uri = crearTempUri()
                    tempCameraUri = uri
                    cameraLauncher.launch(uri)
                }) {
                    Text("Cámara-")
                }

                Button(onClick = { galleryLauncher.launch("image/*") }) { Text("Galería")}
            }

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = viewModel::onNombreChange,
                label = { Text("Nombre") },
                isError = errores.nombreError != null,
                supportingText = { errores.nombreError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.correo,
                onValueChange = viewModel::onCorreoChange,
                label = { Text("Correo") },
                isError = errores.correoError != null,
                supportingText = { errores.correoError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.clave,
                onValueChange = viewModel::onClaveChange,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = errores.claveError != null,
                supportingText = { errores.claveError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.direccion,
                onValueChange = viewModel::onDireccionChange,
                label = { Text("Direccion") },
                isError = errores.direccionError != null,
                supportingText = { errores.direccionError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Checkbox(checked = uiState.aceptaTerminos, onCheckedChange = viewModel::onTerminosChange)
                Text("Acepto Términos y Condiciones")
            }
            errores.terminosError?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            Button(
                onClick = {}
            ) { }

            // TODO
        }
    }
}