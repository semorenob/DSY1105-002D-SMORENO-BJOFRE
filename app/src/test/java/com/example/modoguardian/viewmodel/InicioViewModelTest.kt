package com.example.modoguardian.viewmodel

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Pruebas unitarias de InicioViewModel")
class InicioViewModelTest {

    private lateinit var viewModel: InicioViewModel

    @BeforeEach
    fun setUp() {
        viewModel = InicioViewModel()
    }

    @Test
    @DisplayName("El dashboard parte con cámaras y eventos de ejemplo")
    fun estadoInicial_contieneDatosDelDashboard() {
        val estado = viewModel.uiState.value

        estado.camaras shouldHaveSize 4
        estado.eventos shouldHaveSize 3
        estado.totalCamaras shouldBe 4
        estado.camarasActivas shouldBe 3
        estado.camarasAtencion shouldBe 1
    }

    @Test
    @DisplayName("Los contadores se calculan desde la lista de cámaras")
    fun contadores_seCalculanDesdeLasCamaras() {
        val estado = viewModel.uiState.value

        estado.totalCamaras shouldBe estado.camaras.size
        estado.camarasActivas shouldBe estado.camaras.count { it.enLinea }
        estado.camarasAtencion shouldBe estado.camaras.count { !it.enLinea }
    }

    @Test
    @DisplayName("La cámara sin conexión se identifica correctamente")
    fun camaraSinConexion_esLaDeAtencion() {
        val camaraSinSenal = viewModel.uiState.value.camaras.first { !it.enLinea }

        camaraSinSenal.id shouldBe "CAM-04"
        camaraSinSenal.senal shouldBe 0
        camaraSinSenal.grabando shouldBe false
    }

    @Test
    @DisplayName("El primer evento es crítico y no está confirmado")
    fun primerEvento_esCritico() {
        val evento = viewModel.uiState.value.eventos.first()

        evento.critico shouldBe true
        evento.confirmado shouldBe false
        evento.id shouldBe "EV-1042"
    }
}
