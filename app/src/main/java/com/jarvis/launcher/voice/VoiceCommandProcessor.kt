package com.jarvis.launcher.voice

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

sealed interface VoiceCommand { data class Speak(val text: String): VoiceCommand; data class Launch(val packageHint: String): VoiceCommand; data class Web(val query: String): VoiceCommand; data object Home: VoiceCommand }

@Singleton
class VoiceCommandProcessor @Inject constructor(@ApplicationContext private val context: Context) {
    fun parse(input: String): VoiceCommand { val text = input.removePrefix("Hey Jarvis").trim(); return when {
        text.contains("youtube", true) && text.contains("search", true) -> VoiceCommand.Web("site:youtube.com ${text.substringAfter("search", "")}")
        text.startsWith("search", true) -> VoiceCommand.Web(text.substringAfter("search", "").ifBlank { text })
        text.contains("settings", true) -> VoiceCommand.Launch(Settings.ACTION_SETTINGS)
        text.contains("home", true) -> VoiceCommand.Home
        else -> VoiceCommand.Launch(text.removePrefix("open").trim())
    } }
    fun execute(command: VoiceCommand): String = when(command) {
        is VoiceCommand.Web -> { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=${Uri.encode(command.query)}")).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)); "Searching ${command.query}" }
        is VoiceCommand.Launch -> { if (command.packageHint == Settings.ACTION_SETTINGS) context.startActivity(Intent(Settings.ACTION_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)); "Opening ${command.packageHint}" }
        is VoiceCommand.Speak -> command.text; VoiceCommand.Home -> "Already home"
    }
}
