package com.example.modoguardian.navigation

import androidx.navigation.NavController
import com.example.modoguardian.model.Rol

fun NavController.irAlHome(rol: Rol) {
    val destino = when (rol) {
        Rol.ADMIN -> Screen.HomeAdmin
        Rol.SUPERVISOR -> Screen.HomeSupervisor
        Rol.OPERADOR -> Screen.HomeOperador
    }

    navigate(destino.route) {
        popUpTo(Screen.LoginScreen.route) { inclusive = true }
    }
}
