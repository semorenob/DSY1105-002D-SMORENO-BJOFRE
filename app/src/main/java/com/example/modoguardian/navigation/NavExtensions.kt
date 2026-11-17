package com.example.modoguardian.navigation

import androidx.navigation.NavController
import com.example.modoguardian.model.Rol

fun homeRouteFor(rol: Rol): String = when (rol) {
    Rol.ADMIN -> Screen.HomeAdmin.route
    Rol.SUPERVISOR -> Screen.HomeSupervisor.route
    Rol.OPERADOR -> Screen.HomeOperador.route
}

fun NavController.irAlHome(rol: Rol) {
    navigate(homeRouteFor(rol)) {
        popUpTo(Screen.LoginScreen.route) { inclusive = true }
    }
}
