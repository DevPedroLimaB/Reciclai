package com.example.reciclai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reciclai.shared.util.Resource
import com.example.reciclai.ui.theme.*
import com.example.reciclai.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    onLogout: () -> Unit,
    onAchievementsClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val userState by authViewModel.userState.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GreenPrimary,
                        GreenSecondary,
                        Color.White
                    )
                )
            )
    ) {
        // Header customizado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Meu Perfil",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Seção do perfil
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(GreenLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "👤",
                            fontSize = 48.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    when (val state = userState) {
                        is Resource.Success -> {
                            val user = state.data
                            Text(
                                text = user?.name ?: "Usuário",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GreenDark
                                )
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = user?.email ?: "usuario@email.com",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = GreenDark.copy(alpha = 0.7f)
                                )
                            )
                        }

                        is Resource.Loading -> {
                            CircularProgressIndicator(
                                color = GreenPrimary,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        else -> {
                            Text(
                                text = "Carregando...",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = GreenDark.copy(alpha = 0.7f)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Estatísticas
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatisticItem(
                            value = "12",
                            label = "Pontos\nVisitados",
                            icon = "📍"
                        )
                        StatisticItem(
                            value = "47kg",
                            label = "Material\nReciclado",
                            icon = "♻️"
                        )
                        StatisticItem(
                            value = "230",
                            label = "Pontos\nGanhos",
                            icon = "🏆"
                        )
                    }
                }
            }

            // Menu de opções
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    ProfileMenuItem(
                        icon = Icons.Default.Edit,
                        title = "Editar Perfil",
                        subtitle = "Altere suas informações pessoais",
                        onClick = { /* TODO: Navegar para edição */ }
                    )

                    ProfileMenuItem(
                        icon = Icons.Default.Notifications,
                        title = "Notificações",
                        subtitle = "Configure alertas e lembretes",
                        onClick = { /* TODO: Navegar para notificações */ }
                    )

                    ProfileMenuItem(
                        icon = Icons.Default.History,
                        title = "Histórico",
                        subtitle = "Veja suas atividades de reciclagem",
                        onClick = onHistoryClick
                    )

                    ProfileMenuItem(
                        icon = Icons.Default.EmojiEvents,
                        title = "Conquistas",
                        subtitle = "Suas medalhas e recompensas",
                        onClick = onAchievementsClick
                    )

                    ProfileMenuItem(
                        icon = Icons.Default.Help,
                        title = "Ajuda e Suporte",
                        subtitle = "Tire suas dúvidas",
                        onClick = { /* TODO: Navegar para ajuda */ }
                    )
                }
            }

            // Seção de configurações
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    ProfileMenuItem(
                        icon = Icons.Default.Settings,
                        title = "Configurações",
                        subtitle = "Preferências do aplicativo",
                        onClick = onSettingsClick
                    )

                    ProfileMenuItem(
                        icon = Icons.Default.PrivacyTip,
                        title = "Privacidade",
                        subtitle = "Políticas e termos de uso",
                        onClick = { /* TODO: Navegar para privacidade */ }
                    )
                }
            }

            // Botão de logout
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { showLogoutDialog = true },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFEBEE)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.ExitToApp,
                        contentDescription = "Sair",
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "Sair do App",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFD32F2F)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Dialog de confirmação de logout
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text(
                    text = "Sair do App",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = GreenDark
                    )
                )
            },
            text = {
                Text(
                    text = "Tem certeza que deseja sair do ReciclAI?",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = GreenDark.copy(alpha = 0.8f)
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        authViewModel.logout()
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F)
                    )
                ) {
                    Text("Sair", color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showLogoutDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun StatisticItem(
    value: String,
    label: String,
    icon: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = icon,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = GreenPrimary
            )
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = GreenDark.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
private fun ProfileMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = GreenPrimary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = GreenDark
                    )
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = GreenDark.copy(alpha = 0.7f)
                    )
                )
            }

            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "Ir para $title",
                tint = GreenDark.copy(alpha = 0.5f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
