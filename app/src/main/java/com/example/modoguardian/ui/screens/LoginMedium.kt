package com.example.modoguardian.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.modoguardian.navigation.Screen
import com.example.modoguardian.navigation.irAlHome
import com.example.modoguardian.ui.components.LoginForm
import com.example.modoguardian.viewmodel.LoginViewModel

@Composable
fun LoginMedium(
    navController: NavController,
    viewModel: LoginViewModel
) {
    val estado by viewModel.estado.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 480.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp, vertical = 28.dp)
        ) {
            LoginForm(
                estado = estado,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                onRecordarmeChange = viewModel::onRecordarmeChange,
                onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
                onSubmit = {
                    viewModel.iniciarSesion { rol -> navController.irAlHome(rol) }
                },
                onCreateAccount = { navController.navigate(Screen.RegistroIntegralScreen.route) }
            )
        }
    }
}
