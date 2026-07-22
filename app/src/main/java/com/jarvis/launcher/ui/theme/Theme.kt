package com.jarvis.launcher.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val JarvisDarkColorScheme = darkColorScheme(
    primary = JarvisColors.NeonBlue,
    onPrimary = Color.Black,
    primaryContainer = JarvisColors.DeepNavy,
    onPrimaryContainer = JarvisColors.TextPrimary,
    secondary = JarvisColors.Violet,
    onSecondary = Color.White,
    tertiary = JarvisColors.ArcReactor,
    background = JarvisColors.SpaceBlack,
    onBackground = JarvisColors.TextPrimary,
    surface = JarvisColors.DeepNavy,
    onSurface = JarvisColors.TextPrimary,
    surfaceVariant = JarvisColors.Glass,
    onSurfaceVariant = JarvisColors.TextSecondary,
    error = JarvisColors.ErrorRed,
)

private val JarvisLightColorScheme = lightColorScheme(
    primary = Color(0xFF00687A),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB5EBFF),
    onPrimaryContainer = Color(0xFF001F28),
    secondary = Color(0xFF5B4A99),
    background = Color(0xFFF6FCFF),
    onBackground = Color(0xFF061F28),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF061F28),
    surfaceVariant = Color(0xFFE6F2F6),
    onSurfaceVariant = Color(0xFF415B64),
    error = JarvisColors.ErrorRed,
)

@Composable
fun JarvisTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && darkTheme -> dynamicDarkColorScheme(context)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> dynamicLightColorScheme(context)
        darkTheme -> JarvisDarkColorScheme
        else -> JarvisLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as? Activity)?.window
        if (window != null) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalJarvisSpacing provides JarvisSpacing(),
        LocalJarvisElevation provides JarvisElevation(),
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = JarvisTypography,
            shapes = JarvisShapes,
            content = content,
        )
    }
}
