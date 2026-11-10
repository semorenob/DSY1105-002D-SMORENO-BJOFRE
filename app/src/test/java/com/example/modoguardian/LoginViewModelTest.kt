package com.example.modoguardian.viewmodel

import com.example.modoguardian.model.Rol
import com.example.modoguardian.ui.utils.USUARIOS_PREDEFINIDOS
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel()
    }

    @After
    fun tearDown() {
        kotlinx.coroutines.Dispatchers.resetMain()
    }

    @Test
    fun validarFormulario_conEmailInvalido_devuelveFalso() {
        viewModel.onEmailChange("correo-sin-arroba")
        viewModel.onPasswordChange("123456")

        val resultado = viewModel.validarFormulario()

        assertFalse(resultado)
        assertEquals("Correo invalido", viewModel.estado.value.errores.email)
    }

    @Test
    fun validarFormulario_conPasswordCorto_devuelveFalso() {
        viewModel.onEmailChange("admin@guardian.test")
        viewModel.onPasswordChange("123")

        val resultado = viewModel.validarFormulario()

        assertFalse(resultado)
        assertEquals("Debe tener al menos 6 caracteres", viewModel.estado.value.errores.password)
    }

    @Test
    fun validarFormulario_conDatosValidos_devuelveVerdadero() {
        viewModel.onEmailChange("admin@guardian.test")
        viewModel.onPasswordChange("123456")

        val resultado = viewModel.validarFormulario()

        assertTrue(resultado)
    }

    @Test
    fun iniciarSesion_conCredencialesIncorrectas_muestraError() = runTest(testDispatcher) {
        viewModel.onEmailChange("admin@guardian.test")
        viewModel.onPasswordChange("incorrecta")

        var rolCapturado: Rol? = null
        viewModel.iniciarSesion { rol ->
            rolCapturado = rol
        }

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(null, rolCapturado)
        assertEquals("Correo o contraseña incorrectos", viewModel.estado.value.errores.credenciales)
        assertFalse(viewModel.estado.value.isLoading)
    }

    @Test
    fun iniciarSesion_conCredencialesCorrectas_asignaRolExitosamente() = runTest(testDispatcher) {
        viewModel.onEmailChange("admin@guardian.test")
        viewModel.onPasswordChange("123456")

        var rolCapturado: Rol? = null
        viewModel.iniciarSesion { rol ->
            rolCapturado = rol
        }

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(Rol.ADMIN, rolCapturado)
        assertEquals(Rol.ADMIN, viewModel.estado.value.rol)
        assertFalse(viewModel.estado.value.isLoading)
    }
}
