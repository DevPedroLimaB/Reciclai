package com.example.reciclai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reciclai.ui.theme.*
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var emailEnabled by remember { mutableStateOf(false) }
    var locationEnabled by remember { mutableStateOf(true) }
    var publicProfile by remember { mutableStateOf(false) }

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
            Text(
                text = "⚙️ Configurações",
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Notificações
            SettingsSection("Notificações")
            SettingsToggleItem(
                icon = "🔔",
                title = "Notificações Push",
                description = "Receber alertas sobre pontos próximos",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
            SettingsToggleItem(
                icon = "📧",
                title = "Email",
                description = "Receber newsletter semanal",
                checked = emailEnabled,
                onCheckedChange = { emailEnabled = it }
            )

            // Privacidade
            SettingsSection("Privacidade")
            SettingsToggleItem(
                icon = "📍",
                title = "Localização",
                description = "Permitir acesso à localização",
                checked = locationEnabled,
                onCheckedChange = { locationEnabled = it }
            )
            SettingsToggleItem(
                icon = "👥",
                title = "Perfil Público",
                description = "Mostrar no ranking público",
                checked = publicProfile,
                onCheckedChange = { publicProfile = it }
            )

            // Preferências
            SettingsSection("Preferências")
            SettingsOptionItem(
                icon = "🌍",
                title = "Idioma",
                value = "Português (Brasil)"
            )
            SettingsOptionItem(
                icon = "🎨",
                title = "Tema",
                value = "Claro"
            )
            SettingsOptionItem(
                icon = "📏",
                title = "Unidade",
                value = "Quilogramas (kg)"
            )

            // Conta
            SettingsSection("Conta")
            SettingsButtonItem(
                icon = "🔑",
                title = "Alterar Senha",
                color = Color(0xFFFF9800)
            )
            SettingsButtonItem(
                icon = "🗑️",
                title = "Excluir Conta",
                color = Color(0xFFF44336)
            )
        }
    }
}

@Composable
fun SettingsSection(title: String) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelMedium.copy(
            color = GreenDark.copy(alpha = 0.7f),
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
    )
}

@Composable
fun SettingsToggleItem(
    icon: String,
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = GreenDark
                    )
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = GreenDark.copy(alpha = 0.7f)
                    )
                )
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = GreenPrimary
                )
            )
        }
    }
}

@Composable
fun SettingsOptionItem(icon: String, title: String, value: String, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                fontSize = 24.sp,
                modifier = Modifier.semantics { contentDescription = "$title icon" }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = GreenDark
                    )
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = GreenDark.copy(alpha = 0.7f)
                    )
                )
            }
            Text(text = "›", fontSize = 20.sp, color = GreenDark.copy(alpha = 0.5f))
        }
    }
}

@Composable
fun SettingsButtonItem(icon: String, title: String, color: Color, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                fontSize = 24.sp,
                modifier = Modifier.semantics { contentDescription = "$title icon" }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = color
                ),
                modifier = Modifier.weight(1f)
            )
            Text(text = "›", fontSize = 20.sp, color = GreenDark.copy(alpha = 0.5f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    ReciclaiTheme {
        SettingsScreen(onBackClick = {})
    }
}
