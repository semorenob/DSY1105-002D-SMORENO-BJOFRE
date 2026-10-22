package com.example.modoguardian.model

import android.net.Uri

data class UsuarioErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val clave: String? = null,
    val direccion: String? = null,
    val fotoUri: Uri? = null
) {
}