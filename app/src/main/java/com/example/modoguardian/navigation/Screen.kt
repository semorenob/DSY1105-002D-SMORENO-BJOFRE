package com.example.modoguardian.navigation

sealed class Screen(val route: String) {
    data object LoginScreen: Screen("login")
    data object RegistroIntegralScreen: Screen("registro")
    data object HomeAdmin: Screen("home_admin")
    data object HomeSupervisor: Screen("home_supervisor")
    data object HomeOperador: Screen("home_operador")
    data object Camaras: Screen("camaras")
    data object Eventos: Screen("eventos")
    data object AlertaCritica: Screen("alerta_critica")
    data object Perfil: Screen("perfil")
    data object ResumenScreen: Screen("resumen")
    data object ListadoOfflineScreen: Screen("listado")
    data object SuccessScreen: Screen("success")
    data object LoginSuccessScreen: Screen("login_success")
}
