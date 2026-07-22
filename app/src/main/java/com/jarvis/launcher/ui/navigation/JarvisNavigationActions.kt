package com.jarvis.launcher.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

/** Navigation facade used by UI modules so screens do not duplicate NavOptions. */
class JarvisNavigationActions(private val navController: NavController) {
    fun navigate(destination: JarvisDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun back(): Boolean = navController.popBackStack()
}
