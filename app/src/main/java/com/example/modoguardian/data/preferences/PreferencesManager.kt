package com.example.modoguardian.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class PreferencesManager(private val context: Context) {
    companion object {
        val MODO_ESPECIAL_KEY = booleanPreferencesKey("modo_especial")
        val MODO_OSCURO_KEY = booleanPreferencesKey("modo_oscuro")
    }

    val modoEspecialFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[MODO_ESPECIAL_KEY] ?: false
    }

    val modoOscuroFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[MODO_OSCURO_KEY] ?: true
    }

    suspend fun guardarModoEspecial(activo: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[MODO_ESPECIAL_KEY] = activo
        }
    }

    suspend fun guardarModoOscuro(activo: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[MODO_OSCURO_KEY] = activo
        }
    }
}