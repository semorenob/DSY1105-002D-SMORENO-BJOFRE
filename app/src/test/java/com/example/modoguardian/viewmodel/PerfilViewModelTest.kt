package com.example.modoguardian.viewmodel

import android.net.Uri
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Pruebas unitarias de PerfilViewModel")
class PerfilViewModelTest {

    private lateinit var viewModel: PerfilViewModel

    @BeforeEach
    fun setUp() {
        viewModel = PerfilViewModel()
    }

    @Test
    @DisplayName("El estado inicial mantiene las preferencias activas")
    fun estadoInicial_tienePreferenciasActivas() {
        viewModel.uiState.value.twoFactor shouldBe true
        viewModel.uiState.value.pushNotifications shouldBe true
        viewModel.uiState.value.criticalAlerts shouldBe true
        viewModel.imageUri.value.shouldBeNull()
    }

    @Test
    @DisplayName("Se puede actualizar la URI de la foto de perfil")
    fun setImageUri_actualizaLaFoto() {
        val uri = mockk<Uri>(relaxed = true)

        viewModel.setImageUri(uri)

        viewModel.imageUri.value shouldBe uri
    }

    @Test
    @DisplayName("Se puede limpiar la foto de perfil")
    fun setImageUri_nulo_limpiaLaFoto() {
        viewModel.setImageUri(mockk<Uri>(relaxed = true))

        viewModel.setImageUri(null)

        viewModel.imageUri.value.shouldBeNull()
    }

    @Test
    @DisplayName("Los interruptores de seguridad actualizan su estado")
    fun togglesSeguridad_actualizanEstado() {
        viewModel.onTwoFactorChange(false)
        viewModel.onPushNotificationsChange(false)
        viewModel.onCriticalAlertsChange(false)

        viewModel.uiState.value.twoFactor shouldBe false
        viewModel.uiState.value.pushNotifications shouldBe false
        viewModel.uiState.value.criticalAlerts shouldBe false

        viewModel.onTwoFactorChange(true)
        viewModel.onPushNotificationsChange(true)
        viewModel.onCriticalAlertsChange(true)

        viewModel.uiState.value.twoFactor shouldBe true
        viewModel.uiState.value.pushNotifications shouldBe true
        viewModel.uiState.value.criticalAlerts shouldBe true
    }
}
