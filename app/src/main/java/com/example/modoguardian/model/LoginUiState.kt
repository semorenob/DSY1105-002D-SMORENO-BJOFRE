package com.example.modoguardian.model

data class LoginUiState (
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val rol: Rol? = null,
    val recordarme: Boolean = true,
    val passwordVisible: Boolean = false,
    val errores: LoginErrores = LoginErrores()
)
