package com.example.modoguardian.viewmodel

import com.example.modoguardian.model.Rol
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
@DisplayName("Pruebas unitarias de LoginViewModel")
class LoginViewModelTest {

    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    @DisplayName("Un correo sin @ invalida el formulario")
    fun validarFormulario_conEmailInvalido_devuelveFalso() {
        viewModel.onEmailChange("correo-sin-arroba")
        viewModel.onPasswordChange("123456")

        val resultado = viewModel.validarFormulario()

        resultado shouldBe false
        viewModel.estado.value.errores.email shouldBe "Correo invalido"
        viewModel.estado.value.errores.password.shouldBeNull()
    }

    @Test
    @DisplayName("Una contraseña corta invalida el formulario")
    fun validarFormulario_conPasswordCorto_devuelveFalso() {
        viewModel.onEmailChange("admin@guardian.test")
        viewModel.onPasswordChange("123")

        val resultado = viewModel.validarFormulario()

        resultado shouldBe false
        viewModel.estado.value.errores.password shouldBe "Debe tener al menos 6 caracteres"
        viewModel.estado.value.errores.email.shouldBeNull()
    }

    @Test
    @DisplayName("Un formulario con datos válidos es aceptado")
    fun validarFormulario_conDatosValidos_devuelveVerdadero() {
        viewModel.onEmailChange("admin@guardian.test")
        viewModel.onPasswordChange("123456")

        val resultado = viewModel.validarFormulario()

        resultado shouldBe true
        viewModel.estado.value.errores.email.shouldBeNull()
        viewModel.estado.value.errores.password.shouldBeNull()
    }

    @Test
    @DisplayName("Las credenciales incorrectas muestran un error y no entregan rol")
    fun iniciarSesion_conCredencialesIncorrectas_muestraError() = runTest(testDispatcher) {
        viewModel.onEmailChange("admin@guardian.test")
        viewModel.onPasswordChange("incorrecta")

        var rolCapturado: Rol? = null
        viewModel.iniciarSesion { rol -> rolCapturado = rol }

        testDispatcher.scheduler.advanceUntilIdle()

        rolCapturado.shouldBeNull()
        viewModel.estado.value.errores.credenciales shouldBe "Correo o contraseña incorrectos"
        viewModel.estado.value.isLoading shouldBe false
    }

    @Test
    @DisplayName("Las credenciales correctas asignan el rol y llaman al callback")
    fun iniciarSesion_conCredencialesCorrectas_asignaRolExitosamente() = runTest(testDispatcher) {
        viewModel.onEmailChange("admin@guardian.test")
        viewModel.onPasswordChange("123456")

        var rolCapturado: Rol? = null
        viewModel.iniciarSesion { rol -> rolCapturado = rol }

        testDispatcher.scheduler.advanceUntilIdle()

        rolCapturado shouldBe Rol.ADMIN
        viewModel.estado.value.rol shouldBe Rol.ADMIN
        viewModel.estado.value.isLoading shouldBe false
        viewModel.estado.value.errores.credenciales.shouldBeNull()
    }

    @Test
    @DisplayName("Cambiar el correo limpia los errores de email y credenciales")
    fun onEmailChange_limpiaErroresRelacionados() {
        viewModel.onEmailChange("incorrecto")
        viewModel.onPasswordChange("123456")
        viewModel.validarFormulario()
        viewModel.estado.value.errores.email shouldBe "Correo invalido"

        viewModel.onEmailChange("correo@valido.cl")

        viewModel.estado.value.email shouldBe "correo@valido.cl"
        viewModel.estado.value.errores.email.shouldBeNull()
        viewModel.estado.value.errores.credenciales.shouldBeNull()
    }

    @Test
    @DisplayName("Cerrar sesión reinicia el estado")
    fun cerrarSesion_reiniciaElEstado() {
        viewModel.onEmailChange("admin@guardian.test")
        viewModel.onPasswordChange("123456")
        viewModel.onRecordarmeChange(false)

        viewModel.cerrarSesion()

        viewModel.estado.value.email shouldBe ""
        viewModel.estado.value.password shouldBe ""
        viewModel.estado.value.recordarme shouldBe true
        viewModel.estado.value.rol.shouldBeNull()
    }
}
