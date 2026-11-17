package com.example.modoguardian.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.modoguardian.R
import com.example.modoguardian.navigation.Screen
import com.example.modoguardian.ui.components.HomeLayout
import com.example.modoguardian.ui.components.InicioContent
import com.example.modoguardian.ui.components.RoleTool
import com.example.modoguardian.viewmodel.InicioViewModel
import com.example.modoguardian.viewmodel.LoginViewModel

@Composable
fun HomeSupervisorScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val estado by loginViewModel.estado.collectAsState()
    val inicioViewModel: InicioViewModel = viewModel()
    val inicio by inicioViewModel.uiState.collectAsState()

    HomeLayout(
        titulo = stringResource(R.string.dashboard_hello_supervisor),
        email = estado.email.ifBlank { "supervisor@insercode.cl" },
        rol = "Supervisor",
        onCerrarSesion = {
            loginViewModel.cerrarSesion()
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(0) { inclusive = true }
            }
        },
        onNotificaciones = { navController.navigate(Screen.AlertaCritica.route) }
    ) {
        InicioContent(
            state = inicio,
            tools = listOf(
                RoleTool(
                    stringResource(R.string.tools_registered_users),
                    stringResource(R.string.tools_registered_users_desc),
                    Screen.ListadoOfflineScreen.route
                ),
                RoleTool(
                    stringResource(R.string.tools_summary),
                    stringResource(R.string.tools_summary_desc),
                    Screen.ResumenScreen.route
                ),
                RoleTool(
                    stringResource(R.string.tools_offline),
                    stringResource(R.string.tools_offline_desc),
                    Screen.ListadoOfflineScreen.route
                )
            ),
            onVerTodasCamaras = { navController.navigate(Screen.Camaras.route) },
            onCamaraClick = { navController.navigate(Screen.Camaras.route) },
            onVerEventos = { navController.navigate(Screen.Eventos.route) },
            onToolClick = { navController.navigate(it.route) }
        )
    }
}
