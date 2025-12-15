package com.example.reciclai.ui.screens

import android.Manifest
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

/**
 * Tela de Mapa integrada com Google Maps API
 * Mostra pontos de reciclagem em um mapa real
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MapScreenWithGoogleMaps(
    onProfileClick: () -> Unit,
    onAddPointClick: () -> Unit,
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val recyclingPointsState by mapViewModel.recyclingPointsState.collectAsState()
    var selectedMaterial by remember { mutableStateOf<String?>(null) }
    var selectedPoint by remember { mutableStateOf<RecyclingPoint?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    val materials = listOf("Papel", "Plástico", "Vidro", "Metal", "Eletrônicos", "Óleo")

    // Posição inicial do mapa (São Paulo como exemplo)
    val defaultLocation = LatLng(-23.550520, -46.633308)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 12f)
    }

    // Solicita permissões de localização
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(Unit) {
        mapViewModel.loadRecyclingPoints()
        locationPermissions.launchMultiplePermissionRequest()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Google Maps
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = locationPermissions.allPermissionsGranted
            ),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = true
            )
        ) {
            // Adiciona marcadores para cada ponto de reciclagem
            when (val state = recyclingPointsState) {
                is Resource.Success -> {
                    state.data?.forEach { point ->
                        Marker(
                            state = MarkerState(position = LatLng(point.latitude, point.longitude)),
                            title = point.name,
                            snippet = point.address,
                            onClick = {
                                selectedPoint = point
                                showBottomSheet = true
                                false
                            }
                        )
                    }
                }
                else -> {}
            }
        }

        // Header com título e botão de perfil
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Top Bar - apenas botão de perfil
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onProfileClick,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(GreenPrimary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Perfil",
                        tint = Color.White
                    )
                }
            }

            // Filtros de materiais
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(materials) { material ->
                    FilterChip(
                        selected = selectedMaterial == material,
                        onClick = {
                            selectedMaterial = if (selectedMaterial == material) {
                                null
                            } else {
                                material
                            }
                            mapViewModel.filterByMaterial(selectedMaterial)
                        },
                        label = { Text(material) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = GreenPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Botão flutuante para adicionar ponto
        FloatingActionButton(
            onClick = onAddPointClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = GreenPrimary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Adicionar Ponto",
                tint = Color.White
            )
        }

        // Bottom Sheet com detalhes do ponto selecionado
        if (showBottomSheet && selectedPoint != null) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false }
            ) {
                PointDetailsContent(
                    point = selectedPoint!!,
                    onDismiss = { showBottomSheet = false }
                )
            }
        }

        // Loading indicator
        if (recyclingPointsState is Resource.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = GreenPrimary
            )
        }
    }
}

@Composable
fun PointDetailsContent(
    point: RecyclingPoint,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = point.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = GreenDark
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = GreenPrimary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = point.address,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Materiais aceitos:",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(point.acceptedMaterials) { material ->
                AssistChip(
                    onClick = { },
                    label = { Text(material) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = GreenLight
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenPrimary
            )
        ) {
            Text("Fechar")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
