package com.example.reciclai.ui.screen.rewards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.reciclai.model.Reward
import com.example.reciclai.util.Resource
import com.example.reciclai.viewmodel.RewardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardsScreen(
    rewardViewModel: RewardViewModel
) {
    val rewards by rewardViewModel.rewards.collectAsState()
    val userRewards by rewardViewModel.userRewards.collectAsState()
    val redeemState by rewardViewModel.redeemState.collectAsState()
    
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Disponíveis", "Minhas Recompensas")
    
    LaunchedEffect(Unit) {
        rewardViewModel.getRewards(available = true)
        rewardViewModel.getUserRewards(used = false)
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { Text("Recompensas") }
        )
        
        // User points summary
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Stars,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                
                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = "Seus Pontos",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "1,250", // Would come from user data
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
        
        // Tabs
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        
        // Content based on selected tab
        when (selectedTab) {
            0 -> AvailableRewardsContent(
                rewards = rewards,
                onRedeemReward = { rewardViewModel.redeemReward(it.id) }
            )
            1 -> UserRewardsContent(userRewards = userRewards)
        }
        
        // Handle redeem state
        LaunchedEffect(redeemState) {
            when (redeemState) {
                is Resource.Success -> {
                    // Show success message and refresh user rewards
                    rewardViewModel.getUserRewards(used = false)
                    rewardViewModel.clearRedeemState()
                }
                is Resource.Error -> {
                    // Show error message
                }
                else -> {}
            }
        }
    }
}

@Composable
fun AvailableRewardsContent(
    rewards: Resource<List<Reward>>?,
    onRedeemReward: (Reward) -> Unit
) {
    when (rewards) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is Resource.Success -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(rewards.data ?: emptyList()) { reward ->
                    RewardCard(
                        reward = reward,
                        onRedeem = { onRedeemReward(reward) }
                    )
                }
            }
        }
        is Resource.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = rewards.message ?: "Erro ao carregar recompensas",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        }
        else -> {}
    }
}

@Composable
fun UserRewardsContent(
    userRewards: Resource<List<com.example.reciclai.model.UserReward>>?
) {
    when (userRewards) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is Resource.Success -> {
            val rewards = userRewards.data ?: emptyList()
            if (rewards.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.CardGiftcard,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                        Text(
                            text = "Nenhuma recompensa resgatada",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 16.dp),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(rewards) { userReward ->
                        UserRewardCard(userReward = userReward)
                    }
                }
            }
        }
        is Resource.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userRewards.message ?: "Erro ao carregar suas recompensas",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        }
        else -> {}
    }
}

@Composable
fun RewardCard(
    reward: Reward,
    onRedeem: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = reward.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = reward.description,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Stars,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${reward.pointsCost} pontos",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
                
                Button(
                    onClick = onRedeem,
                    enabled = reward.isAvailable
                ) {
                    Text("Resgatar")
                }
            }
        }
    }
}

@Composable
fun UserRewardCard(
    userReward: com.example.reciclai.model.UserReward
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = userReward.reward.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = "Código: ${userReward.couponCode}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Text(
                        text = "Resgatado em: ${userReward.redeemedAt}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                
                if (userReward.isUsed) {
                    Chip(
                        onClick = { },
                        label = { Text("Usado") },
                        colors = ChipDefaults.chipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                } else {
                    Chip(
                        onClick = { },
                        label = { Text("Disponível") },
                        colors = ChipDefaults.chipColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    }
}