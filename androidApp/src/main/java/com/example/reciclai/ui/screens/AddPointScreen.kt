package com.example.reciclai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reciclai.shared.util.Resource
import com.example.reciclai.ui.theme.*
import com.example.reciclai.viewmodel.MapViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPointScreen(
    onBackClick: () -> Unit,
    onPointAdded: () -> Unit,
    mapViewModel: MapViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var schedule by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedMaterials by remember { mutableStateOf(setOf<String>()) }
    var isLoading by remember { mutableStateOf(false) }

    val addPointState by mapViewModel.addPointState.collectAsState()
    val focusManager = LocalFocusManager.current

    val availableMaterials = listOf(
        "Papel", "Papelão", "Plástico", "Vidro", "Metal", "Alumínio",
        "Eletrônicos", "Pilhas", "Óleo de Cozinha", "Roupas", "Móveis"
    )

    val isFormValid = name.isNotBlank() && address.isNotBlank() &&
                     schedule.isNotBlank() && selectedMaterials.isNotEmpty()

    LaunchedEffect(addPointState) {
        when (addPointState) {
            is Resource.Loading -> {
                isLoading = true
            }
            is Resource.Success -> {
                isLoading = false
                onPointAdded()
            }
            is Resource.Error -> {
                isLoading = false
                // TODO: Mostrar erro
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GreenPrimary,
                        GreenSecondary,
                        Color.White
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header customizado
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
                    text = "Adicionar Ponto",
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
                    .padding(16.dp)
            ) {
                // Card principal
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        // Ícone e título
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Card(
                                modifier = Modifier.size(60.dp),
                                shape = RoundedCornerShape(30.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = GreenLight
                                )
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

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = "Novo Ponto de Coleta",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GreenDark
                                    )
                                )
                                Text(
                                    text = "Ajude sua comunidade a reciclar",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = GreenDark.copy(alpha = 0.7f)
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Campo Nome
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Nome do local") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Business,
                                    contentDescription = "Nome",
                                    tint = GreenPrimary
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GreenPrimary,
                                focusedLabelColor = GreenPrimary,
                                cursorColor = GreenPrimary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Campo Endereço
                        OutlinedTextField(
                            value = address,
                            onValueChange = { address = it },
                            label = { Text("Endereço completo") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.LocationOn,
                                    contentDescription = "Endereço",
                                    tint = GreenPrimary
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GreenPrimary,
                                focusedLabelColor = GreenPrimary,
                                cursorColor = GreenPrimary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Campo Telefone
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("Telefone (opcional)") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Phone,
                                    contentDescription = "Telefone",
                                    tint = GreenPrimary
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GreenPrimary,
                                focusedLabelColor = GreenPrimary,
                                cursorColor = GreenPrimary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Campo Horário
                        OutlinedTextField(
                            value = schedule,
                            onValueChange = { schedule = it },
                            label = { Text("Horário de funcionamento") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Schedule,
                                    contentDescription = "Horário",
                                    tint = GreenPrimary
                                )
                            },
                            placeholder = { Text("Ex: Seg-Sex 8h às 17h") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GreenPrimary,
                                focusedLabelColor = GreenPrimary,
                                cursorColor = GreenPrimary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Campo Descrição
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Descrição (opcional)") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Description,
                                    contentDescription = "Descrição",
                                    tint = GreenPrimary
                                )
                            },
                            placeholder = { Text("Informações adicionais sobre o local") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3,
                            maxLines = 5,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.clearFocus() }
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GreenPrimary,
                                focusedLabelColor = GreenPrimary,
                                cursorColor = GreenPrimary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Seleção de materiais
                        Text(
                            text = "Materiais aceitos *",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = GreenDark
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Selecione os tipos de materiais recicláveis aceitos neste ponto:",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = GreenDark.copy(alpha = 0.7f)
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Grid de materiais
                        availableMaterials.chunked(2).forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                rowItems.forEach { material ->
                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable {
                                                selectedMaterials = if (selectedMaterials.contains(material)) {
                                                    selectedMaterials - material
                                                } else {
                                                    selectedMaterials + material
                                                }
                                            }
                                            .background(
                                                if (selectedMaterials.contains(material)) {
                                                    GreenPrimary.copy(alpha = 0.1f)
                                                } else {
                                                    Color.Transparent
                                                },
                                                RoundedCornerShape(8.dp)
                                            )
                                            .padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = selectedMaterials.contains(material),
                                            onCheckedChange = { checked ->
                                                selectedMaterials = if (checked) {
                                                    selectedMaterials + material
                                                } else {
                                                    selectedMaterials - material
                                                }
                                            },
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = GreenPrimary,
                                                uncheckedColor = GreenDark.copy(alpha = 0.6f)
                                            )
                                        )

                                        Spacer(modifier = Modifier.width(4.dp))

                                        Text(
                                            text = material,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = GreenDark,
                                                fontWeight = if (selectedMaterials.contains(material)) {
                                                    FontWeight.Medium
                                                } else {
                                                    FontWeight.Normal
                                                }
                                            )
                                        )
                                    }
                                }

                                // Se há apenas um item na linha, adiciona um espaço vazio
                                if (rowItems.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // Botão salvar
                        Button(
                            onClick = {
                                if (isFormValid) {
                                    mapViewModel.addRecyclingPoint(
                                        name = name,
                                        address = address,
                                        phone = phone.ifBlank { null },
                                        schedule = schedule,
                                        description = description.ifBlank { null },
                                        acceptedMaterials = selectedMaterials.toList()
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            enabled = isFormValid && !isLoading,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GreenPrimary,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp
                            )
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = "Adicionar",
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Adicionar Ponto",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                            }
                        }

                        if (!isFormValid) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "* Preencha todos os campos obrigatórios",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFFD32F2F)
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
