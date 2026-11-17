package com.example.modoguardian

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Prueba base del source set de tests unitarios locales.
 */
class ExampleUnitTest {

    @Test
    @DisplayName("La suma básica es correcta")
    fun addition_isCorrect() {
        (2 + 2) shouldBe 4
    }
}
