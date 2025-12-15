package com.example.reciclai.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reciclai.shared.model.Content
import com.example.reciclai.shared.util.Resource
import com.example.reciclai.ui.theme.*
import com.example.reciclai.viewmodel.ContentViewModel

@Composable
fun ContentScreen(
    viewModel: ContentViewModel = hiltViewModel()
) {
    val contentState by viewModel.contentState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GreenPrimary,
                        GreenSecondary,
                        GreenLight.copy(alpha = 0.3f),
                        Color.White
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Conteúdo principal
            when (val state = contentState) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = GreenPrimary,
                                strokeWidth = 3.dp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Carregando conteúdos...",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = GreenDark
                                )
                            )
                        }
                    }
                }

                is Resource.Success -> {
                    val contents = state.data ?: emptyList()

                    if (contents.isEmpty()) {
                        EmptyState(modifier = Modifier.fillMaxSize())
                    } else {
                        ContentList(contents = contents)
                    }
                }

                is Resource.Error -> {
                    ErrorState(
                        message = state.message ?: "Erro desconhecido",
                        onRetry = { viewModel.refresh() },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun ContentList(
    contents: List<Content>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(contents) { content ->
            ContentCard(content = content)
        }

        // Espaçador final
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ContentCard(content: Content) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Toast.makeText(context, "Abrindo: ${content.title}", Toast.LENGTH_SHORT).show()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Categoria badge
            Surface(
                modifier = Modifier.clip(RoundedCornerShape(20.dp)),
                color = when (content.category) {
                    "Reciclagem" -> GreenSecondary.copy(alpha = 0.2f)
                    "Sustentabilidade" -> GreenTertiary.copy(alpha = 0.2f)
                    "Localização" -> GreenAccent.copy(alpha = 0.2f)
                    else -> GreenLight
                }
            ) {
                Text(
                    text = content.category,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = GreenDark
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = content.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = GreenDark
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = content.summary,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = GreenDark.copy(alpha = 0.8f),
                    lineHeight = 24.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Footer com informações
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = "Tempo de leitura",
                        tint = GreenAccent,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${content.readTime} min",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = GreenDark.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                if (content.author != null) {
                    Text(
                        text = "por ${content.author}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = GreenDark.copy(alpha = 0.6f),
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "🌱",
                fontSize = 64.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Nenhum conteúdo encontrado",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = GreenDark
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Novos conteúdos educativos serão adicionados em breve!",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = GreenDark.copy(alpha = 0.7f)
                ),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "🚫",
                fontSize = 64.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Ops! Algo deu errado",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = GreenDark
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.error
                ),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenPrimary
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Tentar novamente",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
