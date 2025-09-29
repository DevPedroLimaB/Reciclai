package com.example.reciclai.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.reciclai.ui.screen.community.CommunityScreen
import com.example.reciclai.ui.screen.content.ContentListScreen
import com.example.reciclai.ui.screen.home.HomeScreen
import com.example.reciclai.ui.screen.map.MapScreen
import com.example.reciclai.ui.screen.profile.ProfileScreen
import com.example.reciclai.ui.screen.rewards.RewardsScreen
import com.example.reciclai.ui.screen.scanner.ScannerScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Início", Icons.Default.Home)
    object Map : Screen("map", "Mapa", Icons.Default.LocationOn)
    object Scanner : Screen("scanner", "Scanner", Icons.Default.QrCodeScanner)
    object Rewards : Screen("rewards", "Recompensas", Icons.Default.CardGiftcard)
    object Content : Screen("content", "Conteúdo", Icons.Default.Article)
    object Community : Screen("community", "Comunidade", Icons.Default.Group)
    object Profile : Screen("profile", "Perfil", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReciclaiNavigation() {
    val navController = rememberNavController()
    
    val items = listOf(
        Screen.Home,
        Screen.Map,
        Screen.Scanner,
        Screen.Rewards,
        Screen.Community
    )
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { 
                HomeScreen(
                    onNavigateToMap = { navController.navigate(Screen.Map.route) },
                    onNavigateToScanner = { navController.navigate(Screen.Scanner.route) },
                    onNavigateToProfile = { navController.navigate(Screen.Profile.route) }
                )
            }
            composable(Screen.Map.route) { 
                MapScreen(hiltViewModel())
            }
            composable(Screen.Scanner.route) { 
                ScannerScreen()
            }
            composable(Screen.Rewards.route) { 
                RewardsScreen(hiltViewModel())
            }
            composable(Screen.Content.route) { 
                ContentListScreen(hiltViewModel())
            }
            composable(Screen.Community.route) { 
                CommunityScreen(hiltViewModel())
            }
            composable(Screen.Profile.route) { 
                ProfileScreen(hiltViewModel())
            }
        }
    }
}