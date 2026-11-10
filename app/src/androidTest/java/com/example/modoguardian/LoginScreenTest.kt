package com.example.modoguardian

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.modoguardian.ui.screens.LoginCompact
import com.example.modoguardian.viewmodel.LoginViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        viewModel = LoginViewModel()
    }

    @Test
    fun loginScreen_muestraElementosPrincipales() {
        composeTestRule.setContent {
            LoginCompact(navController = navController, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Iniciar Sesión").assertIsDisplayed()
        composeTestRule.onNodeWithText("Correo electronico").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()
    }

    @Test
    fun loginScreen_muestraErrorCuandoCorreoEsInvalido() {
        composeTestRule.setContent {
            LoginCompact(navController = navController, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Correo electronico").performTextInput("correo-invalido")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("123456")

        // Hacemos click en el botón de Iniciar Sesión para activar la validación
        composeTestRule.onNodeWithText("Iniciar Sesión").performClick()

        // Verificamos que aparece el mensaje de error de correo inválido
        composeTestRule.onNodeWithText("Correo invalido").assertIsDisplayed()
    }
}
