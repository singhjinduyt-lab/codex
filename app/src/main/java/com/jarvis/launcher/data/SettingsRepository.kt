package com.jarvis.launcher.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("jarvis_settings")

data class LauncherSettings(val provider: String = "Gemini", val accent: Long = 0xFF00D8FFFF, val animations: Boolean = true)

@Singleton
class SettingsRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private val provider = stringPreferencesKey("provider"); private val accent = longPreferencesKey("accent"); private val animations = booleanPreferencesKey("animations")
    val settings = context.dataStore.data.map { LauncherSettings(it[provider] ?: "Gemini", it[accent] ?: 0xFF00D8FFFF, it[animations] ?: true) }
    suspend fun setProvider(value: String) = context.dataStore.edit { it[provider] = value }
    suspend fun setAnimations(value: Boolean) = context.dataStore.edit { it[animations] = value }
}
