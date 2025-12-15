package com.example.reciclai.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reciclai.ui.screens.*
import com.example.reciclai.viewmodel.AuthViewModel

/**
 * Navegação principal do app ReciclAI
 * Integra todas as telas essenciais do sistema de reciclagem
 *
 * PASSAGEM DE DADOS ENTRE TELAS:
 * - Usa Navigation Component com argumentos tipados
 * - Suporta dados simples (String, Int) e complexos (via ViewModel compartilhado)
 */
@Composable
fun ReciclaiNavigation(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    // Determina a tela inicial baseada no status de login
    val startDestination = if (authViewModel.isLoggedIn()) "map" else "splash"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Tela de Splash/Introdução
        composable("splash") {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate("welcome") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        // Tela de Boas-vindas
        composable("welcome") {
            WelcomeScreen(
                onEnterClick = {
                    navController.navigate("login")
                }
            )
        }

        // Tela de Login
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    // Navega para o mapa após login bem-sucedido
                    navController.navigate("map") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onRegisterClick = {
                    navController.navigate("register")
                },
                onForgotPasswordClick = {
                    navController.navigate("forgot_password")
                }
            )
        }

        // Tela de Cadastro
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    // Navega para o mapa após registro bem-sucedido
                    navController.navigate("map") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onLoginClick = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = false }
                    }
                }
            )
        }

        // Tela Principal - Mapa com Google Maps API integrado
        composable("map") {
            MapScreenWithGoogleMaps(
                onProfileClick = {
                    navController.navigate("profile")
                },
                onAddPointClick = {
                    navController.navigate("add_point")
                }
            )
        }

        // Tela de Perfil do Usuário
        composable("profile") {
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("welcome") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onAchievementsClick = {
                    navController.navigate("achievements")
                },
                onHistoryClick = {
                    navController.navigate("history")
                },
                onSettingsClick = {
                    navController.navigate("settings")
                },
                onAboutClick = {
                    navController.navigate("about")
                },
                authViewModel = authViewModel
            )
        }

        // Tela de Adição de Novo Ponto de Reciclagem
        composable("add_point") {
            AddPointScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onPointAdded = {
                    // Retorna para o mapa após adicionar ponto
                    navController.popBackStack()
                }
            )
        }

        // Nova rota: Detalhes de um ponto específico
        composable(
            route = "point_detail/{pointId}",
            arguments = listOf(
                navArgument("pointId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val pointId = backStackEntry.arguments?.getString("pointId") ?: ""
            // PointDetailScreen receberia o pointId como parâmetro
            // PointDetailScreen(pointId = pointId, onBackClick = { navController.popBackStack() })
        }

        // Tela de Conteúdo Educativo
        composable("content") {
            ContentScreen()
        }

        // Tela de Esqueci minha senha
        composable("forgot_password") {
            ForgotPasswordScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onEmailSent = {
                    navController.popBackStack()
                }
            )
        }

        // Tela de Conquistas
        composable("achievements") {
            AchievementsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Tela de Histórico
        composable("history") {
            HistoryScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Tela de Configurações
        composable("settings") {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Tela Sobre o App
        composable("about") {
            AboutScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
