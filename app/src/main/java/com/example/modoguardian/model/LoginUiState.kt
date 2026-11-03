package com.example.modoguardian.model

data class LoginUiState (
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val rol: Rol? = null,
    val errores: LoginErrores = LoginErrores()
) {

}
