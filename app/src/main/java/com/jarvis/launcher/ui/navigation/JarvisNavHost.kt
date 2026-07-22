package com.jarvis.launcher.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jarvis.launcher.ui.apps.AppDrawerScreen
import com.jarvis.launcher.ui.chat.ChatScreen
import com.jarvis.launcher.ui.home.HomeScreen
import com.jarvis.launcher.ui.search.SearchScreen
import com.jarvis.launcher.ui.settings.SettingsScreen

@Composable
fun JarvisNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: JarvisDestination = JarvisDestination.Home,
) {
    val actions = remember(navController) { JarvisNavigationActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        composable(JarvisDestination.Home.route) {
            HomeScreen(navController = navController, navigationActions = actions)
        }
        composable(JarvisDestination.Search.route) {
            SearchScreen(navController = navController, navigationActions = actions)
        }
        composable(JarvisDestination.Apps.route) {
            AppDrawerScreen(navController = navController, navigationActions = actions)
        }
        composable(JarvisDestination.Chat.route) {
            ChatScreen(navController = navController, navigationActions = actions)
        }
        composable(JarvisDestination.Settings.route) {
            SettingsScreen(navController = navController, navigationActions = actions)
        }
    }
}
