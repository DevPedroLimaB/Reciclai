package com.example.reciclai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.reciclai.shared.model.RecyclingPoint
import com.example.reciclai.shared.util.Resource
import com.example.reciclai.ui.theme.*
import com.example.reciclai.viewmodel.MapViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    onProfileClick: () -> Unit,
    onAddPointClick: () -> Unit,
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val recyclingPointsState by mapViewModel.recyclingPointsState.collectAsState()
    var selectedMaterial by remember { mutableStateOf<String?>(null) }
    var selectedPoint by remember { mutableStateOf<RecyclingPoint?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    val materials = listOf("Papel", "Plástico", "Vidro", "Metal", "Eletrônicos", "Óleo")

    LaunchedEffect(Unit) {
        mapViewModel.loadRecyclingPoints()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Botão de perfil no topo
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onProfileClick) {
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = "Perfil",
                        tint = GreenPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Filtros de materiais
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(materials) { material ->
                    FilterChip(
                        onClick = {
                            selectedMaterial = if (selectedMaterial == material) null else material
                            mapViewModel.filterByMaterial(selectedMaterial)
                        },
                        label = {
                            Text(
                                text = material,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        selected = selectedMaterial == material,
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color.White,
                            selectedContainerColor = GreenPrimary,
                            labelColor = GreenDark,
                            selectedLabelColor = Color.White
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = GreenPrimary,
                            selectedBorderColor = GreenPrimary
                        )
                    )
                }
            }

            // Área do mapa (simulada)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                GreenLight.copy(alpha = 0.3f),
                                Color.White
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                when (val state = recyclingPointsState) {
                    is Resource.Loading -> {
                        CircularProgressIndicator(
                            color = GreenPrimary,
                            strokeWidth = 3.dp
                        )
                    }

                    is Resource.Success -> {
                        val points = state.data ?: emptyList()
                        MapSimulationView(
                            recyclingPoints = points,
                            onPointClick = { point ->
                                selectedPoint = point
                                showBottomSheet = true
                            }
                        )
                    }

                    is Resource.Error -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "⚠️",
                                fontSize = 48.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Erro ao carregar pontos",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = GreenDark
                                ),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { mapViewModel.loadRecyclingPoints() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GreenPrimary
                                )
                            ) {
                                Text("Tentar novamente", color = Color.White)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // FAB para adicionar novo ponto
        FloatingActionButton(
            onClick = onAddPointClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = GreenPrimary,
            contentColor = Color.White
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Adicionar ponto de reciclagem"
            )
        }

        // Bottom Sheet com detalhes do ponto
        if (showBottomSheet && selectedPoint != null) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false }
            ) {
                RecyclingPointBottomSheet(
                    point = selectedPoint!!,
                    onDismiss = { showBottomSheet = false }
                )
            }
        }
    }
}

@Composable
private fun MapSimulationView(
    recyclingPoints: List<RecyclingPoint>,
    onPointClick: (RecyclingPoint) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Simula um mapa com pontos
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            recyclingPoints.take(6).forEachIndexed { index, point ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = when {
                        index % 3 == 0 -> Arrangement.Start
                        index % 3 == 1 -> Arrangement.Center
                        else -> Arrangement.End
                    }
                ) {
                    MapPoint(
                        point = point,
                        onClick = { onPointClick(point) }
                    )
                }
            }
        }

        // Indicação de mapa
        Text(
            text = "🗺️ Mapa Interativo",
            style = MaterialTheme.typography.titleMedium.copy(
                color = GreenDark.copy(alpha = 0.6f)
            ),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        )
    }
}

@Composable
private fun MapPoint(
    point: RecyclingPoint,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(60.dp)
            .clickable { onClick() },
        shape = CircleShape,
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
                text = "📍",
                fontSize = 24.sp
            )
        }
    }
}

@Composable
private fun RecyclingPointBottomSheet(
    point: RecyclingPoint,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = point.name,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = GreenDark
                )
            )
            IconButton(onClick = onDismiss) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Fechar",
                    tint = GreenDark
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Endereço
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.LocationOn,
                contentDescription = "Endereço",
                tint = GreenPrimary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = point.address,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = GreenDark
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Horário
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Schedule,
                contentDescription = "Horário",
                tint = GreenPrimary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = point.schedule,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = GreenDark
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Materiais aceitos
        Text(
            text = "Materiais aceitos:",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = GreenDark
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(point.acceptedMaterials) { material ->
                AssistChip(
                    onClick = { },
                    label = {
                        Text(
                            text = material,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = GreenLight,
                        labelColor = GreenDark
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botões de ação
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { /* TODO: Abrir direções */ },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Default.Directions,
                    contentDescription = "Direções",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Direções")
            }

            Button(
                onClick = { /* TODO: Ligar para o local */ },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenPrimary
                )
            ) {
                Icon(
                    Icons.Default.Phone,
                    contentDescription = "Ligar",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Ligar", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
