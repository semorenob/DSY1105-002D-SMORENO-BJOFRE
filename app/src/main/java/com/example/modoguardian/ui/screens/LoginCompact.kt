package com.example.modoguardian.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
            modifier = Modifier.fillMaxWidth()
        )

        // Contrañeña
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
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (viewModel.validarFormulario()) {
                    navController.navigate("success")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }

        // Boton para navegar a pantalla de registro
        Button(
            onClick = { navController.navigate("registro") },
            modifier = Modifier
        ) {
            Text("¿No tienes una cuenta? Registrate.")
        }
    }
}