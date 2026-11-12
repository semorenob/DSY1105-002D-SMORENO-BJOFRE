package com.example.modoguardian.ui.utils

import com.example.modoguardian.model.Rol

// ADMINistrador
const val EMAIL_ADMIN = "admin@guardian.test"
const val PASSWD_ADMIN = "123456"

// SUPERVISOR
const val EMAIL_SUPERVISOR = "supervisor@guardian.test"
const val PASSWD_SUPERVISOR = "123456"

// OPERADOR
const val EMAIL_OPERADOR = "operador@guardian.test"
const val PASSWD_OPERADOR = "123456"

// GUARDIAN (cuenta demo de los mockups)
const val EMAIL_GUARDIAN = "guardian@insercode.cl"
const val PASSWD_GUARDIAN = "123456"

data class UsuarioPredefinido(
    val email: String,
    val password: String,
    val rol: Rol
)

val USUARIOS_PREDEFINIDOS = listOf(
    UsuarioPredefinido(EMAIL_ADMIN, PASSWD_ADMIN, Rol.ADMIN),
    UsuarioPredefinido(EMAIL_SUPERVISOR, PASSWD_SUPERVISOR, Rol.SUPERVISOR),
    UsuarioPredefinido(EMAIL_OPERADOR, PASSWD_OPERADOR, Rol.OPERADOR),
    UsuarioPredefinido(EMAIL_GUARDIAN, PASSWD_GUARDIAN, Rol.OPERADOR)
)
