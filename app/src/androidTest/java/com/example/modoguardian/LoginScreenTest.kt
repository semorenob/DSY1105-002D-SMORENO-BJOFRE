package com.example.modoguardian

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
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

        composeTestRule.onNodeWithText("Ingresar al sistema").assertIsDisplayed()
        composeTestRule.onNodeWithText("CORREO ELECTRÓNICO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONTRASEÑA").assertIsDisplayed()
    }

    @Test
    fun loginScreen_muestraErrorCuandoCorreoEsInvalido() {
        composeTestRule.setContent {
            LoginCompact(navController = navController, viewModel = viewModel)
        }

        composeTestRule.onNodeWithTag("login_email").performTextInput("correo-invalido")
        composeTestRule.onNodeWithTag("login_password").performTextInput("123456")

        // Hacemos click en el botón de ingreso para activar la validación
        composeTestRule.onNodeWithText("Ingresar al sistema").performClick()

        // Verificamos que aparece el mensaje de error de correo inválido
        composeTestRule.onNodeWithText("Correo invalido").assertIsDisplayed()
    }
}
