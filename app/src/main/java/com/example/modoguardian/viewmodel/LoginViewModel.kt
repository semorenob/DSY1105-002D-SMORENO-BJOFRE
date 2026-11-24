package com.example.modoguardian.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modoguardian.data.GoogleAuthManager
import com.example.modoguardian.data.ResultadoGoogle
import com.example.modoguardian.model.LoginErrores
import com.example.modoguardian.model.LoginUiState
import com.example.modoguardian.model.Rol
import com.example.modoguardian.ui.utils.USUARIOS_PREDEFINIDOS
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val googleAuthManager: GoogleAuthManager = GoogleAuthManager()
) : ViewModel() {

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

    /**
     * Inicio de sesion con Google + Firebase Auth (Credential Manager).
     *
     * @param activity   Activity necesaria para lanzar el dialogo de Google.
     * @param onExito    Callback de navegacion (igual que iniciarSesion).
     */
    fun iniciarSesionConGoogle(activity: Activity, onExito: (Rol) -> Unit) {
        // Evita lanzar dos veces el dialogo si ya hay una peticion en curso.
        if (_estado.value.isLoading) return

        viewModelScope.launch {
            _estado.update {
                it.copy(
                    isLoading = true,
                    errores = it.errores.copy(credenciales = null)
                )
            }

            when (val resultado = googleAuthManager.signInConGoogle(activity)) {
                is ResultadoGoogle.Exito -> {
                    // Si el correo de la cuenta Google coincide con un usuario
                    // predefinido se conserva su rol; si no, entra como OPERADOR.
                    val rol = rolParaEmail(resultado.email)
                    _estado.update {
                        it.copy(isLoading = false, rol = rol)
                    }
                    onExito(rol)
                }

                is ResultadoGoogle.Error -> _estado.update {
                    it.copy(
                        isLoading = false,
                        errores = it.errores.copy(credenciales = resultado.mensaje)
                    )
                }

                ResultadoGoogle.Cancelado -> _estado.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    private fun rolParaEmail(email: String?): Rol {
        if (email.isNullOrBlank()) return Rol.OPERADOR
        return USUARIOS_PREDEFINIDOS
            .firstOrNull { it.email.equals(email.trim(), ignoreCase = true) }
            ?.rol ?: Rol.OPERADOR
    }

    fun cerrarSesion() {
        googleAuthManager.cerrarSesion()
        _estado.value = LoginUiState()
    }
}