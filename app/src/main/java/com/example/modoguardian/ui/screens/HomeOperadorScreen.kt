package com.example.modoguardian.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.modoguardian.navigation.Screen
import com.example.modoguardian.ui.components.HomeLayout
import com.example.modoguardian.ui.components.HomeOptionCard
import com.example.modoguardian.viewmodel.LoginViewModel

@Composable
fun HomeOperadorScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val estado by loginViewModel.estado.collectAsState()

    HomeLayout(
        titulo = "Panel Operador",
        email = estado.email,
        rol = estado.rol?.etiqueta ?: "Operador",
        onCerrarSesion = {
            loginViewModel.cerrarSesion()
            navController.navigate(Screen.StartPage.route) {
                popUpTo(Screen.StartPage.route) { inclusive = true }
            }
        }
    ) {
        HomeOptionCard(
            titulo = "Registrar usuario",
            descripcion = "Registrar una persona con foto y datos"
        ) {
            navController.navigate(Screen.RegistroIntegralScreen.route)
        }

        HomeOptionCard(
            titulo = "Resumen",
            descripcion = "Ver el resumen del último registro"
        ) {
            navController.navigate(Screen.ResumenScreen.route)
        }

        HomeOptionCard(
            titulo = "Listado Offline",
            descripcion = "Consultar los registros guardados"
        ) {
            navController.navigate(Screen.ListadoOfflineScreen.route)
        }
    }
}
