package com.example.reciclai.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reciclai.ui.theme.GreenDark
import com.example.reciclai.ui.theme.GreenLight
import com.example.reciclai.ui.theme.GreenPrimary
import com.example.reciclai.ui.theme.GreenSecondary

@Composable
fun WelcomeScreen(
    onEnterClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GreenLight,
                        Color.White,
                        GreenLight
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // Espaçador superior
            Spacer(modifier = Modifier.height(60.dp))

            // Logo e título principal
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ícone grande do Reciclai
                Card(
                    modifier = Modifier.size(120.dp),
                    shape = RoundedCornerShape(60.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = GreenPrimary
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "♻️",
                            fontSize = 64.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Título
                Text(
                    text = "Reciclai",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = GreenPrimary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Subtítulo
                Text(
                    text = "Transforme o mundo,\numa reciclagem por vez",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = GreenDark,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }

            // Seção de benefícios
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WelcomeFeature(
                    icon = "🌱",
                    title = "Sustentável",
                    description = "Contribua para um planeta mais verde"
                )

                Spacer(modifier = Modifier.height(20.dp))

                WelcomeFeature(
                    icon = "📍",
                    title = "Pontos de Coleta",
                    description = "Encontre locais próximos para reciclar"
                )

                Spacer(modifier = Modifier.height(20.dp))

                WelcomeFeature(
                    icon = "🏆",
                    title = "Recompensas",
                    description = "Ganhe pontos e troque por prêmios"
                )
            }

            // Botão de entrada
            Column {
                Button(
                    onClick = onEnterClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 12.dp
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Começar a Reciclar",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "→",
                            fontSize = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Junte-se a milhares de pessoas que já fazem a diferença!",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = GreenDark.copy(alpha = 0.7f)
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun WelcomeFeature(
    icon: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícone
        Card(
            modifier = Modifier.size(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = GreenSecondary.copy(alpha = 0.1f)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 24.sp
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Texto
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = GreenPrimary
                )
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = GreenDark.copy(alpha = 0.8f)
                )
            )
        }
    }
}
