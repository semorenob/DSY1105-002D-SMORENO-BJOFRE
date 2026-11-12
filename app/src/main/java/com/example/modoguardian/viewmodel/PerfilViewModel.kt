package com.example.modoguardian.viewmodel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

data class PerfilUiState(
    val twoFactor: Boolean = true,
    val pushNotifications: Boolean = true,
    val criticalAlerts: Boolean = true
)

class PerfilViewModel: ViewModel() {

    private val _imageUri = MutableStateFlow<Uri?>(null)

    val imageUri: StateFlow<Uri?> = _imageUri.asStateFlow()

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    fun setImageUri(uri: Uri?) {
        _imageUri.value = uri
    }

    fun onTwoFactorChange(activo: Boolean) {
        _uiState.update { it.copy(twoFactor = activo) }
    }

    fun onPushNotificationsChange(activo: Boolean) {
        _uiState.update { it.copy(pushNotifications = activo) }
    }

    fun onCriticalAlertsChange(activo: Boolean) {
        _uiState.update { it.copy(criticalAlerts = activo) }
    }

    // generar uri temporal para que la camara guarde la foto
    fun createTempImageUri(context: Context): Uri {
        // apunta a carpeta images/ dentro del almacentamiento interno de la app
        val imagesDir = File(context.filesDir, "images")

        if (!imagesDir.exists()) {
            imagesDir.mkdirs()
        }

        val tempFile = File.createTempFile("captured_image_", ".jpg", imagesDir)

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            tempFile
        )
    }
}
