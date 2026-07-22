package com.jarvis.launcher.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class JarvisSpacing(
    val tiny: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp,
)

data class JarvisElevation(
    val glass: Dp = 8.dp,
    val floating: Dp = 16.dp,
)

val LocalJarvisSpacing = staticCompositionLocalOf { JarvisSpacing() }
val LocalJarvisElevation = staticCompositionLocalOf { JarvisElevation() }
