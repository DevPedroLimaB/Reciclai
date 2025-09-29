package com.example.reciclai.ui.screen.scanner

import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ScannerScreen() {
    var scanMode by remember { mutableStateOf(ScanMode.BARCODE) }
    var scanResult by remember { mutableStateOf<ScanResult?>(null) }
    
    val context = LocalContext.current
    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { Text("Scanner de Materiais") }
        )
        
        // Mode selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterChip(
                onClick = { scanMode = ScanMode.BARCODE },
                label = { Text("Código de Barras") },
                selected = scanMode == ScanMode.BARCODE,
                leadingIcon = {
                    Icon(
                        Icons.Default.QrCode,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            )
            
            FilterChip(
                onClick = { scanMode = ScanMode.IMAGE },
                label = { Text("Imagem") },
                selected = scanMode == ScanMode.IMAGE,
                leadingIcon = {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            )
        }
        
        if (cameraPermission.hasPermission) {
            // Camera preview area
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Camera preview would go here
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            if (scanMode == ScanMode.BARCODE) Icons.Default.QrCodeScanner else Icons.Default.CameraAlt,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        
                        Text(
                            text = if (scanMode == ScanMode.BARCODE) {
                                "Aponte a câmera para o código de barras"
                            } else {
                                "Tire uma foto do material para identificação"
                            },
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
            
            // Capture button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                FloatingActionButton(
                    onClick = {
                        // Simulate scan result
                        scanResult = ScanResult(
                            materialName = "Garrafa PET",
                            category = "Plástico",
                            recyclable = true,
                            instructions = "Remova o rótulo e a tampa antes de descartar. Pode ser reciclado em qualquer ponto de coleta de plástico.",
                            pointsValue = 15
                        )
                    },
                    modifier = Modifier.size(72.dp)
                ) {
                    Icon(
                        if (scanMode == ScanMode.BARCODE) Icons.Default.QrCodeScanner else Icons.Default.CameraAlt,
                        contentDescription = "Capturar",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        } else {
            // Permission request
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    
                    Text(
                        text = "Permissão da Câmera",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    
                    Text(
                        text = "Para usar o scanner, precisamos acessar sua câmera",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    
                    Button(
                        onClick = { cameraPermission.launchPermissionRequest() },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Permitir Acesso à Câmera")
                    }
                }
            }
        }
        
        // Scan result
        scanResult?.let { result ->
            ScanResultCard(
                result = result,
                onDismiss = { scanResult = null }
            )
        }
    }
}

@Composable
fun ScanResultCard(
    result: ScanResult,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (result.recyclable) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.errorContainer
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = result.materialName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Fechar")
                }
            }
            
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    if (result.recyclable) Icons.Default.Recycling else Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                
                Text(
                    text = "${result.category} - ${if (result.recyclable) "Reciclável" else "Não reciclável"}",
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Medium
                )
            }
            
            if (result.recyclable) {
                Text(
                    text = "Pontos: +${result.pointsValue}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            Text(
                text = result.instructions,
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 14.sp
            )
        }
    }
}

enum class ScanMode {
    BARCODE, IMAGE
}

data class ScanResult(
    val materialName: String,
    val category: String,
    val recyclable: Boolean,
    val instructions: String,
    val pointsValue: Int
)