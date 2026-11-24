package com.example.modoguardian.data

import android.app.Activity
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialOption
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.example.modoguardian.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

sealed class ResultadoGoogle {
    data class Exito(val nombre: String?, val email: String?) : ResultadoGoogle()
    data class Error(val mensaje: String) : ResultadoGoogle()
    data object Cancelado : ResultadoGoogle()
}

class GoogleAuthManager(
    // Se resuelve de forma perezosa para que crear el manager (p.ej. en tests
    // unitarios de JVM) no requiera que Firebase este inicializado.
    private val firebaseAuthProvider: () -> FirebaseAuth = { FirebaseAuth.getInstance() }
) {
    private val firebaseAuth: FirebaseAuth by lazy(firebaseAuthProvider)

    // Web client ID de Firebase (Authentication -> Google), leido desde BuildConfig.
    private val webClientId: String = BuildConfig.WEB_CLIENT_ID

    suspend fun signInConGoogle(activity: Activity): ResultadoGoogle {
        val credentialManager = CredentialManager.create(activity)

        // 1) Intento rapido: cuentas de Google ya presentes en el dispositivo.
        val opcionCuentas = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false) // muestra TODAS las cuentas
            .setServerClientId(webClientId)
            .setAutoSelectEnabled(false)
            .build()

        return try {
            obtenerCredencial(credentialManager, activity, opcionCuentas)
        } catch (e: NoCredentialException) {
            // 2) Sin cuentas disponibles: abre el flujo completo de Google
            //    ("Iniciar sesion con Google"), que permite agregar o crear una cuenta.
            try {
                val opcionBoton = GetSignInWithGoogleOption.Builder(webClientId).build()
                obtenerCredencial(credentialManager, activity, opcionBoton)
            } catch (e2: GetCredentialCancellationException) {
                ResultadoGoogle.Cancelado
            } catch (e2: NoCredentialException) {
                ResultadoGoogle.Error(
                    "No se pudo iniciar sesion con Google. Verifica que el SHA-1 " +
                            "este registrado en Firebase y que el dispositivo tenga Google Play Services."
                )
            } catch (e2: GetCredentialException) {
                ResultadoGoogle.Error("Google Sign-In fallo (${e2.type}): ${e2.message}")
            } catch (e2: Exception) {
                ResultadoGoogle.Error("Error de Firebase Auth: ${e2.message}")
            }
        } catch (e: GetCredentialCancellationException) {
            ResultadoGoogle.Cancelado
        } catch (e: GetCredentialException) {
            ResultadoGoogle.Error("Google Sign-In fallo (${e.type}): ${e.message}")
        } catch (e: Exception) {
            ResultadoGoogle.Error("Error de Firebase Auth: ${e.message}")
        }
    }

    private suspend fun obtenerCredencial(
        credentialManager: CredentialManager,
        activity: Activity,
        opcion: CredentialOption
    ): ResultadoGoogle {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(opcion)
            .build()

        val credencial = credentialManager.getCredential(activity, request).credential

        if (credencial is CustomCredential &&
            credencial.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            val googleIdToken = GoogleIdTokenCredential.createFrom(credencial.data)
            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken.idToken, null)
            val usuario = firebaseAuth.signInWithCredential(firebaseCredential).await().user
            return ResultadoGoogle.Exito(nombre = usuario?.displayName, email = usuario?.email)
        }
        return ResultadoGoogle.Error("Credencial no reconocida")
    }

    fun cerrarSesion() {
        runCatching { firebaseAuth.signOut() }
    }
}
