package com.example.modoguardian.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modoguardian.model.LoginErrores
import com.example.modoguardian.model.LoginUiState
import com.example.modoguardian.model.Rol
import com.example.modoguardian.ui.utils.USUARIOS_PREDEFINIDOS
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val _estado = MutableStateFlow(LoginUiState())

    val estado: StateFlow<LoginUiState> = _estado

    fun onEmailChange(valor: String) {
        _estado.update {
            it.copy(
                email = valor,
                errores = it.errores.copy(email = null, credenciales = null)
            )
        }
    }

    fun onPasswordChange(valor: String) {
        _estado.update {
            it.copy(
                password = valor,
                errores = it.errores.copy(password = null, credenciales = null)
            )
        }
    }

    fun onRecordarmeChange(valor: Boolean) {
        _estado.update { it.copy(recordarme = valor) }
    }

    fun onTogglePasswordVisibility() {
        _estado.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = LoginErrores(
            email = if (!estadoActual.email.contains("@")) "Correo invalido" else null,
            password = if (estadoActual.password.length < 6) "Debe tener al menos 6 caracteres" else null,
        )

        val hayErrores = listOfNotNull(
            errores.email,
            errores.password
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }

    fun iniciarSesion(onExito: (Rol) -> Unit) {
        if (!validarFormulario()) return

        viewModelScope.launch {
            _estado.update {
                it.copy(
                    isLoading = true,
                    errores = it.errores.copy(credenciales = null)
                )
            }

            delay(900)

            val estadoActual = _estado.value
            val usuario = USUARIOS_PREDEFINIDOS.firstOrNull {
                it.email == estadoActual.email.trim().lowercase() &&
                        it.password == estadoActual.password
            }

            if (usuario == null) {
                _estado.update {
                    it.copy(
                        isLoading = false,
                        errores = it.errores.copy(
                            credenciales = "Correo o contraseña incorrectos"
                        )
                    )
                }
            } else {
                _estado.update {
                    it.copy(isLoading = false, rol = usuario.rol)
                }
                onExito(usuario.rol)
            }
        }
    }

    fun cerrarSesion() {
        _estado.value = LoginUiState()
    }
}
