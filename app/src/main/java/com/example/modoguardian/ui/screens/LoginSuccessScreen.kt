package com.example.modoguardian.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.modoguardian.navigation.Screen
import com.example.modoguardian.navigation.irAlHome
import com.example.modoguardian.viewmodel.LoginViewModel

@Composable
fun LoginSuccessScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val estado by loginViewModel.estado.collectAsState()
    val rol = estado.rol

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("¡Sesión iniciada!", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Bienvenido a ModoGuardian.")

        if (rol != null) {
            Spacer(modifier = Modifier.height(8.dp))

            Text("Correo: ${estado.email}")

            Text("Rol: ${rol.etiqueta}")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.irAlHome(rol) }) {
                Text("Continuar al panel", style = MaterialTheme.typography.labelLarge)
            }

            TextButton(onClick = {
                loginViewModel.cerrarSesion()
                navController.navigate(Screen.StartPage.route) {
                    popUpTo(Screen.StartPage.route) { inclusive = true }
                }
            }) {
                Text("Cerrar sesión")
            }
        } else {
            Spacer(modifier = Modifier.height(8.dp))

            Text("No hay una sesión activa.")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.navigate(Screen.StartPage.route) }) {
                Text("Volver al inicio", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
