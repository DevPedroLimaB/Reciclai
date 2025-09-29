package com.example.reciclai.ui.screen.map

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reciclai.model.CollectPoint
import com.example.reciclai.util.Resource
import com.example.reciclai.viewmodel.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    mapViewModel: MapViewModel
) {
    val collectPoints by mapViewModel.collectPoints.collectAsState()
    val selectedCollectPoint by mapViewModel.selectedCollectPoint.collectAsState()
    val context = LocalContext.current
    
    // Request location permissions
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    
    LaunchedEffect(Unit) {
        mapViewModel.getCollectPoints()
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { Text("Pontos de Coleta") },
            actions = {
                IconButton(onClick = { mapViewModel.getCollectPoints() }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Atualizar")
                }
            }
        )
        
        // Map placeholder (would integrate with Google Maps)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Map,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Mapa dos Pontos de Coleta",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Integração com Google Maps",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
        
        // Collect Points List
        Text(
            text = "Pontos Próximos",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        when (collectPoints) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
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
                    items(collectPoints.data ?: emptyList()) { collectPoint ->
                        CollectPointCard(
                            collectPoint = collectPoint,
                            onClick = { mapViewModel.selectCollectPoint(collectPoint) }
                        )
                    }
                }
            }
            is Resource.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = collectPoints.message ?: "Erro ao carregar pontos de coleta",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            else -> {}
        }
    }
    
    // Bottom sheet for selected collect point
    selectedCollectPoint?.let { collectPoint ->
        CollectPointBottomSheet(
            collectPoint = collectPoint,
            onDismiss = { mapViewModel.selectCollectPoint(collectPoint) },
            onSchedule = { /* Navigate to schedule creation */ }
        )
    }
}

@Composable
fun CollectPointCard(
    collectPoint: CollectPoint,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = collectPoint.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = collectPoint.address,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Schedule,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = collectPoint.operatingHours,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            // Materials accepted
            Text(
                text = "Materiais aceitos:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = collectPoint.acceptedMaterials.joinToString(", "),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectPointBottomSheet(
    collectPoint: CollectPoint,
    onDismiss: () -> Unit,
    onSchedule: () -> Unit
) {
    // This would be implemented with BottomSheetScaffold or ModalBottomSheet
    // For now, showing as a simple dialog
}