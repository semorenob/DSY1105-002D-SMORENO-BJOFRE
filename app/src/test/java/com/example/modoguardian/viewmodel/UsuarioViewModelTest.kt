package com.example.modoguardian.viewmodel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.modoguardian.data.local.UsuarioDao
import com.example.modoguardian.data.local.UsuarioEntity
import com.example.modoguardian.data.preferences.PreferencesManager
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("Pruebas unitarias de UsuarioViewModel")
class UsuarioViewModelTest {

    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private lateinit var dao: UsuarioDao
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var modoEspecialFlow: MutableStateFlow<Boolean>
    private lateinit var viewModel: UsuarioViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        modoEspecialFlow = MutableStateFlow(false)
        dao = mockk(relaxed = true)
        preferencesManager = mockk(relaxed = true)

        every { dao.getUsuariosFlow() } returns flowOf(emptyList())
        every { preferencesManager.modoEspecialFlow } returns modoEspecialFlow
        coEvery { dao.insertar(any()) } just Runs
        coEvery { preferencesManager.guardarModoEspecial(any()) } just Runs

        viewModel = UsuarioViewModel(dao = dao, preferencesManager = preferencesManager)
    }

    @AfterEach
    fun tearDown() {
        viewModel.viewModelScope.cancel()
        Dispatchers.resetMain()
    }

    @Test
    @DisplayName("El estado inicial parte vacío y deja de cargar tras 1,5 segundos")
    fun estadoInicial_cargandoCambiaDespuesDelDelay() = runTest(testDispatcher) {
        viewModel.uiState.value.nombre shouldBe ""
        viewModel.uiState.value.correo shouldBe ""
        viewModel.cargando.value shouldBe true

        testDispatcher.scheduler.advanceTimeBy(1499)
        testDispatcher.scheduler.runCurrent()
        viewModel.cargando.value shouldBe true

        testDispatcher.scheduler.advanceTimeBy(1)
        testDispatcher.scheduler.runCurrent()
        viewModel.cargando.value shouldBe false
    }

    @Test
    @DisplayName("El flujo de modo especial actualiza el estado de la UI")
    fun modoEspecialFlow_actualizaUiState() = runTest(testDispatcher) {
        modoEspecialFlow.value = true

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.value.modoEspecial shouldBe true
    }

    @Test
    @DisplayName("Un formulario incompleto reporta todos los errores")
    fun validarFormulario_incompleto_reportaErrores() {
        val resultado = viewModel.validarFormulario()

        resultado shouldBe false
        viewModel.errores.value.nombreError shouldBe "Mínimo 3 caracteres"
        viewModel.errores.value.correoError shouldBe "Para escribir un correo valido debe incluir @"
        viewModel.errores.value.claveError shouldBe "Mínimo 6 caracteres"
        viewModel.errores.value.direccionError shouldBe "La direccion es obligatoria"
        viewModel.errores.value.terminosError shouldBe "Debe aceptar los terminos"
        viewModel.errores.value.fotoError shouldBe "Debe seleccionar o tomar una foto"
    }

    @Test
    @DisplayName("Un formulario completo y válido no reporta errores")
    fun validarFormulario_valido_devuelveVerdadero() {
        completarFormulario()

        val resultado = viewModel.validarFormulario()

        resultado shouldBe true
        viewModel.errores.value.nombreError.shouldBeNull()
        viewModel.errores.value.correoError.shouldBeNull()
        viewModel.errores.value.claveError.shouldBeNull()
        viewModel.errores.value.direccionError.shouldBeNull()
        viewModel.errores.value.terminosError.shouldBeNull()
        viewModel.errores.value.fotoError.shouldBeNull()
    }

    @Test
    @DisplayName("Guardar un formulario válido inserta en el DAO y ejecuta el callback")
    fun guardarEnRoom_valido_insertaYNotifica() = runTest(testDispatcher) {
        val entidadCapturada = slot<UsuarioEntity>()
        coEvery { dao.insertar(capture(entidadCapturada)) } just Runs
        modoEspecialFlow.value = true
        testDispatcher.scheduler.advanceUntilIdle()
        completarFormulario()

        var exito = false
        viewModel.guardarEnRoom { exito = true }
        testDispatcher.scheduler.advanceUntilIdle()

        exito shouldBe true
        coVerify(exactly = 1) { dao.insertar(any()) }
        entidadCapturada.captured.nombre shouldBe "Ana Pérez"
        entidadCapturada.captured.correo shouldBe "ana@guardian.test"
        entidadCapturada.captured.direccion shouldBe "Av. Siempre Viva 123"
        entidadCapturada.captured.modoEspecial shouldBe true
        entidadCapturada.captured.fotoPath shouldBe "content://fotos/usuario.jpg"
    }

    @Test
    @DisplayName("Un formulario inválido no inserta ni ejecuta el callback")
    fun guardarEnRoom_invalido_noInserta() = runTest(testDispatcher) {
        var exito = false

        viewModel.guardarEnRoom { exito = true }
        testDispatcher.scheduler.advanceUntilIdle()

        exito shouldBe false
        coVerify(exactly = 0) { dao.insertar(any()) }
    }

    @Test
    @DisplayName("Alternar modo especial guarda la preferencia y muestra el mensaje temporal")
    fun toggleModoEspecial_guardaPreferenciaYMensajeTemporal() = runTest(testDispatcher) {
        viewModel.toggleModoEspecial()
        testDispatcher.scheduler.runCurrent()

        coVerify(exactly = 1) { preferencesManager.guardarModoEspecial(true) }
        viewModel.mensajeExito.value shouldBe true

        testDispatcher.scheduler.advanceTimeBy(2000)
        testDispatcher.scheduler.runCurrent()

        viewModel.mensajeExito.value shouldBe false
    }

    @Test
    @DisplayName("Cambiar un campo limpia su error correspondiente")
    fun onNombreChange_limpiaError() {
        viewModel.validarFormulario()
        viewModel.errores.value.nombreError shouldBe "Mínimo 3 caracteres"

        viewModel.onNombreChange("Ana")

        viewModel.uiState.value.nombre shouldBe "Ana"
        viewModel.errores.value.nombreError.shouldBeNull()
    }

    private fun completarFormulario() {
        viewModel.onNombreChange("Ana Pérez")
        viewModel.onCorreoChange("ana@guardian.test")
        viewModel.onClaveChange("123456")
        viewModel.onDireccionChange("Av. Siempre Viva 123")
        viewModel.onTerminosChange(true)
        viewModel.actualizarFoto(uriFoto())
        viewModel.onNombreChange("Ana Pérez")
    }

    private fun uriFoto(): Uri {
        val uri = mockk<Uri>(relaxed = true)
        every { uri.toString() } returns "content://fotos/usuario.jpg"
        return uri
    }
}
