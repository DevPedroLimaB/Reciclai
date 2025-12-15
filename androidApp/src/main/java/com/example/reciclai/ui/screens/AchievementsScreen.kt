package com.example.reciclai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.alpha
import com.example.reciclai.ui.theme.*

data class Achievement(
    val icon: String,
    val title: String,
    val description: String,
    val unlocked: Boolean,
    val progress: Int = 0,
    val maxProgress: Int = 100
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementsScreen(
    onBackClick: () -> Unit
) {
    val achievements = remember {
        listOf(
            Achievement("🌟", "Primeira Reciclagem", "Complete sua primeira reciclagem", true, 1, 1),
            Achievement("🔥", "Em Chamas", "Recicle 5 vezes em uma semana", false, 3, 5),
            Achievement("🏆", "Mestre da Reciclagem", "Alcance 1000 pontos", false, 230, 1000),
            Achievement("🌍", "Guardião do Planeta", "Recicle 100kg de material", false, 47, 100),
            Achievement("💚", "Eco Warrior", "Use o app por 30 dias seguidos", false, 12, 30),
            Achievement("⭐", "Colecionador", "Desbloqueie todas as conquistas", false, 1, 6)
        )
    }

    val unlockedCount = achievements.count { it.unlocked }
    val totalCount = achievements.size

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
            Column {
                Text(
                    text = "🎯 Minhas Conquistas",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = "$unlockedCount de $totalCount desbloqueadas",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.9f)
                    )
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(achievements) { achievement ->
                AchievementCard(achievement)
            }
        }
    }
}

@Composable
fun AchievementCard(achievement: Achievement) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        if (achievement.unlocked) GreenLight else Color.LightGray.copy(alpha = 0.3f),
                        RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = achievement.icon,
                    fontSize = 32.sp,
                    modifier = Modifier.alpha(if (achievement.unlocked) 1f else 0.5f)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = achievement.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (achievement.unlocked) GreenDark else GreenDark.copy(alpha = 0.5f)
                    )
                )
                Text(
                    text = achievement.description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = GreenDark.copy(alpha = 0.7f)
                    )
                )

                if (achievement.unlocked) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "✓ Desbloqueada",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = GreenPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                } else if (achievement.maxProgress > 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = achievement.progress.toFloat() / achievement.maxProgress,
                        modifier = Modifier.fillMaxWidth(),
                        color = GreenPrimary,
                        trackColor = GreenLight
                    )
                    Text(
                        text = "${achievement.progress}/${achievement.maxProgress}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = GreenDark.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        }
    }
}
