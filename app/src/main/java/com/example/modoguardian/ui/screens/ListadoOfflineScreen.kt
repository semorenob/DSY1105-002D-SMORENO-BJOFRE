package com.example.modoguardian.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.modoguardian.ui.components.IntelligentImage
import com.example.modoguardian.viewmodel.UsuarioViewModel

@Composable
fun ListadoOfflineScreen(viewModel: UsuarioViewModel) {
    val usuarios by viewModel.listaUsuariosFlow.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(usuarios) { u ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    IntelligentImage(imageUri = u.fotoPath?.let { Uri.parse(it) }, modifier = Modifier.size(50.dp))
                    Column {
                        Text(u.nombre, style = MaterialTheme.typography.titleMedium)
                        Text(u.correo, style = MaterialTheme.typography.bodySmall)
                        Text("Modo: ${if (u.modoEspecial) "Premium" else "Basico"}")
                    }
                }
            }
        }
    }
}