package com.jarvis.launcher.domain

import android.graphics.drawable.Drawable

data class LauncherApp(val label: String, val packageName: String, val icon: Drawable? = null, val favorite: Boolean = false, val hidden: Boolean = false)
data class SystemStats(val battery: Int, val ram: String, val storage: String, val cpu: String)
data class ChatMessage(val role: String, val content: String)
