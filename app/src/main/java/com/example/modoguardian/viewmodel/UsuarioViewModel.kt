package com.example.modoguardian.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modoguardian.data.local.UsuarioDao
import com.example.modoguardian.data.local.UsuarioEntity
import com.example.modoguardian.data.preferences.PreferencesManager
import com.example.modoguardian.model.UsuarioErrores
import com.example.modoguardian.model.UsuarioUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val dao: UsuarioDao,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(UsuarioUiState())
    val uiState: StateFlow<UsuarioUiState> = _uiState.asStateFlow()

    private val _errores = MutableStateFlow(UsuarioErrores())
    val errores: StateFlow<UsuarioErrores> = _errores.asStateFlow()

    private val _cargando = MutableStateFlow(true)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    private val _mensajeExito = MutableStateFlow(false)
    val mensajeExito: StateFlow<Boolean> = _mensajeExito.asStateFlow()

    val listaUsuariosFlow = dao.getUsuariosFlow()

    init {
        viewModelScope.launch {
            delay(1500)
            _cargando.value = false
        }

        viewModelScope.launch {
            preferencesManager.modoEspecialFlow.collect { activo ->
                _uiState.value = _uiState.value.copy(modoEspecial = activo)
            }
        }
    }

    fun onNombreChange(v: String) { _uiState.value = _uiState.value.copy(nombre = v) }
    fun onCorreoChange(v: String) { _uiState.value = _uiState.value.copy(correo = v) }
    fun onClaveChange(v: String) { _uiState.value = _uiState.value.copy(clave = v) }
    fun onDireccionChange(v: String) { _uiState.value = _uiState.value.copy(direccion = v) }
    fun onTerminosChange(v: Boolean) { _uiState.value = _uiState.value.copy(aceptaTerminos = v) }
    fun actualizarFoto(uri: Uri?) { _uiState.value = _uiState.value.copy(fotoUri = uri) }

    fun toggleModoEspecial() {
        viewModelScope.launch {
            val nuevoEstado = !_uiState.value.modoEspecial
            preferencesManager.guardarModoEspecial(nuevoEstado)
            _mensajeExito.value = true
            delay(2000)
            _mensajeExito.value = false
        }
    }

    fun validarFormulario(): Boolean {
        val s = _uiState.value
        val errNombre = if (s.nombre.length < 3) "Mínimo 3 caracteres" else null
        val errCorreo = if (!s.correo.contains("@")) "Para escribir un correo valido debe incluir @" else null
        val errClave = if (s.clave.length < 6) "Mínimo 6 caracteres" else null
        val errDir = if (s.direccion.isBlank()) "La direccion es obligatoria" else null
        val errTerm = if (!s.aceptaTerminos) "Debe aceptar los terminos" else null
        val errFoto = if (s.fotoUri == null) "Debe seleccionar o tomar una foto" else null

        _errores.value = UsuarioErrores(errNombre, errCorreo, errClave, errDir, errTerm, errFoto)

        return listOf(errNombre, errCorreo, errClave, errDir, errTerm, errFoto).all { it == null }
    }

    fun guardarEnRoom(onExito: () -> Unit) {
        if (!validarFormulario()) return

        viewModelScope.launch {
            val s = _uiState.value
            val entity = UsuarioEntity(
                nombre = s.nombre,
                correo = s.correo,
                dirrecion = s.direccion,
                modoEspecial = s.modoEspecial,
                fotoPath = s.fotoUri?.toString()
            )
            dao.insertar(entity)
            onExito()
        }
    }
}