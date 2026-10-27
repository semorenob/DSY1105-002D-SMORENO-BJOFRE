package com.example.modoguardian.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.modoguardian.ui.components.IntelligentImage
import com.example.modoguardian.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(viewModel: PerfilViewModel = viewModel()) {
    val context = LocalContext.current
    val imageUri by viewModel.imageUri.collectAsState()

    // guarda temporal la uri generada por camara
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    // lanzador de galeria
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.setImageUri(uri)
        }
    }

    // lanzador de camara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success && tempCameraUri != null) {
            viewModel.setImageUri(tempCameraUri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IntelligentImage(imageUri = imageUri)

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // boton galeria
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text("Galería")
            }

            // boton camara
            Button(onClick = {
                val uri = viewModel.createTempImageUri(context)
                tempCameraUri = uri
                cameraLauncher.launch(uri)
            }) {
                Text("Cámara")
            }
        }
    }
}