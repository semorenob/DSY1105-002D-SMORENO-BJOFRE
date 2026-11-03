package com.example.modoguardian.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.modoguardian.navigation.Screen
import com.example.modoguardian.viewmodel.LoginViewModel

@Composable
fun LoginCompact(
    navController: NavController,
    viewModel: LoginViewModel
) {
    val estado by viewModel.estado.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        // Correo
        OutlinedTextField(
            value = estado.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Correo electronico") },
            isError = estado.errores.email != null,
            supportingText = {
                estado.errores.email?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            enabled = !estado.isLoading,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Contraseña
        OutlinedTextField(
            value = estado.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = estado.errores.password != null,
            supportingText = {
                estado.errores.password?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            enabled = !estado.isLoading,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        estado.errores.credenciales?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.iniciarSesion {
                    navController.navigate(Screen.LoginSuccessScreen.route)
                }
            },
            enabled = !estado.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (estado.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Iniciar Sesión")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Boton para navegar a pantalla de registro
        Button(
            onClick = { navController.navigate(Screen.RegistroIntegralScreen.route) },
            enabled = !estado.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿No tienes una cuenta? Registrate.")
        }
    }
}