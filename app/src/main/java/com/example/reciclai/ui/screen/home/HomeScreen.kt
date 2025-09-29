package com.example.reciclai.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reciclai.ui.components.StatCard
import com.example.reciclai.ui.components.QuickActionCard
import com.example.reciclai.viewmodel.UserViewModel
import com.example.reciclai.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToMap: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToProfile: () -> Unit,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val userProfile by userViewModel.userProfile.collectAsState()
    val userStats by userViewModel.userStats.collectAsState()
    
    LaunchedEffect(Unit) {
        userViewModel.getUserProfile()
        userViewModel.getUserStats()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Olá,",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = when (userProfile) {
                                is Resource.Success -> userProfile.data?.name ?: "Usuário"
                                else -> "Usuário"
                            },
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Perfil",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Vamos reciclar e fazer a diferença!",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }
        
        // Stats Section
        Text(
            text = "Suas Estatísticas",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        when (userStats) {
            is Resource.Success -> {
                val stats = userStats.data!!
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = listOf(
                            "Pontos" to "${stats.totalPoints}",
                            "Reciclado" to "${stats.totalRecycled}kg",
                            "CO2 Poupado" to "${stats.co2Saved}kg",
                            "Nível" to "${stats.currentLevel}"
                        )
                    ) { (title, value) ->
                        StatCard(
                            title = title,
                            value = value,
                            modifier = Modifier.width(140.dp)
                        )
                    }
                }
            }
            is Resource.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(4) {
                        StatCard(
                            title = "...",
                            value = "0",
                            modifier = Modifier.width(140.dp)
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Quick Actions
        Text(
            text = "Ações Rápidas",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionCard(
                    title = "Pontos de Coleta",
                    description = "Encontre pontos próximos",
                    icon = Icons.Default.LocationOn,
                    onClick = onNavigateToMap,
                    modifier = Modifier.weight(1f)
                )
                
                QuickActionCard(
                    title = "Scanner",
                    description = "Identifique materiais",
                    icon = Icons.Default.QrCodeScanner,
                    onClick = onNavigateToScanner,
                    modifier = Modifier.weight(1f)
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionCard(
                    title = "Recompensas",
                    description = "Troque seus pontos",
                    icon = Icons.Default.CardGiftcard,
                    onClick = { /* Navigate to rewards */ },
                    modifier = Modifier.weight(1f)
                )
                
                QuickActionCard(
                    title = "Comunidade",
                    description = "Ranking e desafios",
                    icon = Icons.Default.Group,
                    onClick = { /* Navigate to community */ },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Environmental Impact
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Eco,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.size(48.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Impacto Ambiental",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                
                Text(
                    text = when (userStats) {
                        is Resource.Success -> "Você já contribuiu para economizar ${userStats.data!!.co2Saved}kg de CO2 na atmosfera!"
                        else -> "Comece a reciclar e veja seu impacto positivo no meio ambiente!"
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}