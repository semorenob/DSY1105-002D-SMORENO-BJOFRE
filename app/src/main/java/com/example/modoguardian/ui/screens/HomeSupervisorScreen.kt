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
fun HomeSupervisorScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val estado by loginViewModel.estado.collectAsState()

    HomeLayout(
        titulo = "Panel Supervisor",
        email = estado.email,
        rol = estado.rol?.etiqueta ?: "Supervisor",
        onCerrarSesion = {
            loginViewModel.cerrarSesion()
            navController.navigate(Screen.StartPage.route) {
                popUpTo(Screen.StartPage.route) { inclusive = true }
            }
        }
    ) {
        HomeOptionCard(
            titulo = "Usuarios registrados",
            descripcion = "Revisar los usuarios guardados en el dispositivo"
        ) {
            navController.navigate(Screen.ListadoOfflineScreen.route)
        }

        HomeOptionCard(
            titulo = "Resumen",
            descripcion = "Consultar el último registro del equipo"
        ) {
            navController.navigate(Screen.ResumenScreen.route)
        }

        HomeOptionCard(
            titulo = "Listado Offline",
            descripcion = "Ver los registros disponibles sin conexión"
        ) {
            navController.navigate(Screen.ListadoOfflineScreen.route)
        }
    }
}
