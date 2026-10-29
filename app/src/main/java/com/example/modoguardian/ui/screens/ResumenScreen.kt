package com.example.modoguardian.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.modoguardian.ui.components.IntelligentImage
import com.example.modoguardian.viewmodel.UsuarioViewModel

@Composable
fun ResumenScreen(viewModel: UsuarioViewModel, onVerListado: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Resumen del Usuario", style = MaterialTheme.typography.headlineMedium)
        IntelligentImage(imageUri = uiState.fotoUri)

        Text("Nombre: ${uiState.nombre}")
        Text("Correo: ${uiState.correo}")
        Text("Dirección: ${uiState.direccion}")
        Text("Modo: ${if (uiState.modoEspecial) "Premium" else "Basico"}")

        Button(onClick = onVerListado) {
            Text("Ver listado Offline")
        }
    }
}