package com.example.modoguardian.model

import android.net.Uri

data class UsuarioErrores(
    val nombreError: String? = null,
    val correoError: String? = null,
    val claveError: String? = null,
    val direccionError: String? = null,
    val terminosError: String? = null,
    val fotoError: String? = null
) {
}