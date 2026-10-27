package com.example.modoguardian.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.modoguardian.viewmodel.RegistroViewModel

@Composable
fun SuccessScreen(
    navController: NavController,
    viewModel: RegistroViewModel
) {
    val estado by viewModel.estado.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("¡Registrado con Éxito!", style = MaterialTheme.typography.headlineMedium)
        Text("Nombre: ${estado.nombre}")
        Text("Correo: ${estado.correo}")
        //Text("Dirección: ${estado.direccion}")
        //Text("Contraseña: ${"*".repeat(estado.clave.length)}")
        //Text("Términos: ${if (estado.aceptaTerminos) "Aceptados" else "No aceptados"}")
        Text("Ahora puedes volver al inicio e iniciar sesión con tu nueva cuenta.")

        Button(
            onClick = {
                navController.navigate("start_page")
            },
            colors = ButtonDefaults.buttonColors(),
            modifier = Modifier
        ) {
            Text("Volver al inicio", style = MaterialTheme.typography.labelLarge)
        }
    }


}