package com.jarvis.launcher.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val JarvisDark = darkColorScheme(primary = Color(0xFF00D8FF), secondary = Color(0xFF7C4DFF), background = Color(0xFF020712), surface = Color(0xCC071426), onPrimary = Color.Black, onBackground = Color(0xFFE5FAFF), onSurface = Color(0xFFE5FAFF))
@Composable fun JarvisTheme(content: @Composable () -> Unit) = MaterialTheme(colorScheme = JarvisDark, typography = Typography(), content = content)
