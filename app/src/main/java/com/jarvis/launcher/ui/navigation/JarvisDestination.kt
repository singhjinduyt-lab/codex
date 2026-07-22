package com.jarvis.launcher.ui.navigation

import androidx.annotation.DrawableRes

/**
 * Single source of truth for every top-level JARVIS Launcher destination.
 * Keeping routes centralized prevents string drift as launcher features grow.
 */
sealed class JarvisDestination(
    val route: String,
    val title: String,
    @DrawableRes val iconRes: Int? = null,
) {
    data object Home : JarvisDestination("home", "Home")
    data object Search : JarvisDestination("search", "Search")
    data object Apps : JarvisDestination("apps", "App Drawer")
    data object Chat : JarvisDestination("chat", "AI Chat")
    data object Settings : JarvisDestination("settings", "Settings")

    companion object {
        val topLevel = listOf(Home, Search, Apps, Chat, Settings)

        fun fromRoute(route: String?): JarvisDestination = topLevel.firstOrNull { destination ->
            route?.substringBefore("?") == destination.route
        } ?: Home
    }
}
