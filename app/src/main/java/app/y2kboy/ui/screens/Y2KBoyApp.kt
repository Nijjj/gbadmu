package app.y2kboy.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.y2kboy.core.Y2KBoyAppState

private enum class AppRoute(val path: String) {
    Home("home"),
    Game("game"),
    Settings("settings"),
}

@Composable
fun Y2KBoyApp(appState: Y2KBoyAppState) {
    val navController = rememberNavController()
    val items = listOf(AppRoute.Home, AppRoute.Game, AppRoute.Settings)
    val backStack by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                items.forEach { route ->
                    val selected = backStack?.destination?.hierarchy?.any { it.route == route.path } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(route.path) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = when (route) {
                                    AppRoute.Home -> Icons.Outlined.Home
                                    AppRoute.Game -> Icons.Outlined.SportsEsports
                                    AppRoute.Settings -> Icons.Outlined.Settings
                                },
                                contentDescription = route.name,
                            )
                        },
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = AppRoute.Home.path,
            modifier = Modifier.padding(padding),
        ) {
            composable(AppRoute.Home.path) {
                HomeScreen(
                    library = appState.library,
                    onPlay = { navController.navigate(AppRoute.Game.path) },
                    onOpenSettings = { navController.navigate(AppRoute.Settings.path) },
                )
            }
            composable(AppRoute.Game.path) {
                GameScreen(
                    displaySettings = appState.displaySettings,
                    onExit = {
                        navController.navigate(AppRoute.Home.path) {
                            popUpTo(AppRoute.Game.path) { inclusive = true }
                        }
                    },
                )
            }
            composable(AppRoute.Settings.path) {
                SettingsScreen(displaySettings = appState.displaySettings)
            }
        }
    }
}
