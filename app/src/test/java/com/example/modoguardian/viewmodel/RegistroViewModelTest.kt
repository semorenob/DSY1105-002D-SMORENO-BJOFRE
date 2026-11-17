package com.example.modoguardian.viewmodel

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Pruebas unitarias de RegistroViewModel")
class RegistroViewModelTest {

    private lateinit var viewModel: RegistroViewModel

    @BeforeEach
    fun setUp() {
        viewModel = RegistroViewModel()
    }

    @Test
    @DisplayName("Un formulario vacío reporta campos obligatorios y correo inválido")
    fun validarFormulario_vacio_reportaErrores() {
        val resultado = viewModel.validarFormulario()

        resultado shouldBe false
        viewModel.estado.value.errores.nombreError shouldBe "Campo obligatorio"
        viewModel.estado.value.errores.correoError shouldBe "Correo invalido"
        viewModel.estado.value.errores.claveError shouldBe "Debe tener al menos 6 caracteres"
        viewModel.estado.value.errores.direccionError shouldBe "Campo obligatorio"
    }

    @Test
    @DisplayName("Un formulario completo es válido")
    fun validarFormulario_valido_devuelveVerdadero() {
        viewModel.onNombreChange("Ana Pérez")
        viewModel.onCorreoChange("ana@guardian.test")
        viewModel.onClaveChange("123456")
        viewModel.onDireccionChange("Av. Siempre Viva 123")

        val resultado = viewModel.validarFormulario()

        resultado shouldBe true
        viewModel.estado.value.errores.nombreError.shouldBeNull()
        viewModel.estado.value.errores.correoError.shouldBeNull()
        viewModel.estado.value.errores.claveError.shouldBeNull()
        viewModel.estado.value.errores.direccionError.shouldBeNull()
    }

    @Test
    @DisplayName("Cambiar un campo limpia su error")
    fun onNombreChange_limpiaError() {
        viewModel.validarFormulario()
        viewModel.estado.value.errores.nombreError shouldBe "Campo obligatorio"

        viewModel.onNombreChange("Ana")

        viewModel.estado.value.nombre shouldBe "Ana"
        viewModel.estado.value.errores.nombreError.shouldBeNull()
    }

    @Test
    @DisplayName("El checkbox de términos actualiza el estado")
    fun onAceptarTerminosChange_actualizaEstado() {
        viewModel.onAceptarTerminosChange(true)
        viewModel.estado.value.aceptaTerminos shouldBe true

        viewModel.onAceptarTerminosChange(false)
        viewModel.estado.value.aceptaTerminos shouldBe false
    }
}
