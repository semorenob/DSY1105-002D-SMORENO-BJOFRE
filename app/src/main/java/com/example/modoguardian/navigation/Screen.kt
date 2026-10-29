package com.example.modoguardian.navigation

sealed class Screen(val route: String) {
    data object StartPage: Screen("start_page")
    data object LoginScreen: Screen("login")
    data object RegistroIntegralScreen: Screen("registro")
    data object HomeAdmin: Screen("home_admin")
    data object HomeSupervisor: Screen("home_supervisor")
    data object HomeOperador: Screen("home_operador")
    data object ResumenScreen: Screen("resumen")
    data object ListadoOfflineScreen: Screen("listado")
}

