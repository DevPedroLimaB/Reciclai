package com.example.reciclai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reciclai.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(GreenPrimary, GreenSecondary, Color.White)
                )
            )
    ) {
        // Header
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
                text = "ℹ️ Sobre o App",
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Logo e Nome
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(GreenPrimary, GreenSecondary)
                                ),
                                RoundedCornerShape(24.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "♻️", fontSize = 48.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Reciclai",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = GreenPrimary
                        )
                    )

                    Text(
                        text = "Versão 1.0.0",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = GreenDark.copy(alpha = 0.7f)
                        )
                    )
                }
            }

            // Missão
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Nossa Missão",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = GreenDark
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "O Reciclai é uma plataforma que conecta pessoas e empresas aos pontos de coleta de materiais recicláveis, promovendo a sustentabilidade e a preservação do meio ambiente.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = GreenDark.copy(alpha = 0.8f),
                            lineHeight = 24.sp
                        ),
                        textAlign = TextAlign.Justify
                    )
                }
            }

            // Estatísticas
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Impacto Global",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = GreenDark
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    AboutStatItem("🌍", "10.000+", "Usuários ativos")
                    AboutStatItem("📍", "500+", "Pontos de coleta")
                    AboutStatItem("♻️", "50 ton", "Material reciclado")
                    AboutStatItem("🌳", "2.500 ton", "CO₂ evitado")
                }
            }

            // Links
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Links Úteis",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = GreenDark
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    AboutLinkItem("🌐", "Site Oficial", "www.reciclai.com.br")
                    AboutLinkItem("📧", "Suporte", "contato@reciclai.com.br")
                    AboutLinkItem("📱", "Instagram", "@reciclai")
                    AboutLinkItem("📘", "Termos de Uso", "Ver documento")
                    AboutLinkItem("🔒", "Política de Privacidade", "Ver documento")
                }
            }

            // Créditos
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Desenvolvido com 💚 para um planeta melhor",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = GreenDark.copy(alpha = 0.7f),
                            lineHeight = 20.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "© 2025 Reciclai. Todos os direitos reservados.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = GreenDark.copy(alpha = 0.5f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun AboutStatItem(icon: String, value: String, label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = icon, fontSize = 32.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = GreenPrimary
                )
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = GreenDark.copy(alpha = 0.7f)
                )
            )
        }
    }
}

@Composable
fun AboutLinkItem(icon: String, title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = icon, fontSize = 24.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = GreenDark
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = GreenDark.copy(alpha = 0.7f)
                )
            )
        }
        Text(text = "›", fontSize = 20.sp, color = GreenDark.copy(alpha = 0.5f))
    }
}

