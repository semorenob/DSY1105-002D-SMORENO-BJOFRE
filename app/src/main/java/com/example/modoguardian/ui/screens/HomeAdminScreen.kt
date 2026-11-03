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
fun HomeAdminScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val estado by loginViewModel.estado.collectAsState()

    HomeLayout(
        titulo = "Panel Administrador",
        email = estado.email,
        rol = estado.rol?.etiqueta ?: "Administrador",
        onCerrarSesion = {
            loginViewModel.cerrarSesion()
            navController.navigate(Screen.StartPage.route) {
                popUpTo(Screen.StartPage.route) { inclusive = true }
            }
        }
    ) {
        HomeOptionCard(
            titulo = "Usuarios registrados",
            descripcion = "Ver el listado completo de usuarios"
        ) {
            navController.navigate(Screen.ListadoOfflineScreen.route)
        }

        HomeOptionCard(
            titulo = "Registrar usuario",
            descripcion = "Crear un nuevo usuario en la base local"
        ) {
            navController.navigate(Screen.RegistroIntegralScreen.route)
        }

        HomeOptionCard(
            titulo = "Resumen",
            descripcion = "Ver el último usuario registrado"
        ) {
            navController.navigate(Screen.ResumenScreen.route)
        }
    }
}
