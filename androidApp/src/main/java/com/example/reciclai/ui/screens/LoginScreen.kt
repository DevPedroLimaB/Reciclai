package com.example.reciclai.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reciclai.shared.util.Resource
import com.example.reciclai.ui.theme.GreenDark
import com.example.reciclai.ui.theme.GreenLight
import com.example.reciclai.ui.theme.GreenPrimary
import com.example.reciclai.ui.theme.GreenSecondary
import com.example.reciclai.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val authState by authViewModel.authState.collectAsState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    // Validações
    val isEmailValid = email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.length >= 3 // Mínimo 3 caracteres para demo
    val isFormValid = isEmailValid && isPasswordValid

    // Observa mudanças no estado de autenticação
    LaunchedEffect(authState) {
        when (authState) {
            is Resource.Loading -> {
                isLoading = true
                errorMessage = null
            }
            is Resource.Success -> {
                isLoading = false
                errorMessage = null
                if (authState.data != null) {
                    Toast.makeText(context, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    onLoginSuccess()
                }
            }
            is Resource.Error -> {
                isLoading = false
                errorMessage = authState.message
                Toast.makeText(context, "Erro no login: ${authState.message}", Toast.LENGTH_LONG).show()
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
                        GreenLight
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Botão voltar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            // Conteúdo principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                // Logo pequeno
                Card(
                    modifier = Modifier.size(80.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "♻️",
                            fontSize = 40.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Título
                Text(
                    text = "Bem-vindo de volta!",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Faça login para continuar sua jornada sustentável",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White.copy(alpha = 0.9f)
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Card com formulário
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {

                        // Mensagem de erro
                        if (errorMessage != null) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFFFEBEE)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = errorMessage!!,
                                    modifier = Modifier.padding(12.dp),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color(0xFFD32F2F)
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        // Campo Email
                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                email = it
                                errorMessage = null // Limpa erro ao digitar
                            },
                            label = { Text("E-mail") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Email,
                                    contentDescription = "E-mail",
                                    tint = GreenPrimary
                                )
                            },
                            trailingIcon = {
                                if (email.isNotBlank()) {
                                    Icon(
                                        if (isEmailValid) Icons.Default.Check else Icons.Default.Close,
                                        contentDescription = if (isEmailValid) "Email válido" else "Email inválido",
                                        tint = if (isEmailValid) GreenPrimary else Color(0xFFD32F2F),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (email.isNotBlank() && !isEmailValid) Color(0xFFD32F2F) else GreenPrimary,
                                focusedLabelColor = GreenPrimary,
                                cursorColor = GreenPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            isError = email.isNotBlank() && !isEmailValid
                        )

                        if (email.isNotBlank() && !isEmailValid) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Digite um e-mail válido",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFFD32F2F)
                                ),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Campo Senha
                        OutlinedTextField(
                            value = password,
                            onValueChange = {
                                password = it
                                errorMessage = null // Limpa erro ao digitar
                            },
                            label = { Text("Senha") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = "Senha",
                                    tint = GreenPrimary
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = Icons.Default.RemoveRedEye,
                                        contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha",
                                        tint = if (passwordVisible) GreenPrimary else GreenPrimary.copy(alpha = 0.6f)
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    if (isFormValid) {
                                        authViewModel.login(email, password)
                                    }
                                }
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GreenPrimary,
                                focusedLabelColor = GreenPrimary,
                                cursorColor = GreenPrimary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Link "Esqueci a senha"
                        Text(
                            text = "Esqueci minha senha",
                            modifier = Modifier
                                .align(Alignment.End)
                                .clickable {
                                    onForgotPasswordClick()
                                },
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = GreenPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Botão Login
                        Button(
                            onClick = {
                                if (isFormValid) {
                                    authViewModel.login(email, password)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            enabled = isFormValid && !isLoading,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GreenPrimary,
                                contentColor = Color.White,
                                disabledContainerColor = GreenPrimary.copy(alpha = 0.6f)
                            ),
                            shape = RoundedCornerShape(16.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp
                            )
                        ) {
                            if (isLoading) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Entrando...")
                                }
                            } else {
                                Text(
                                    text = "Entrar",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Link "Não tem conta? Cadastre-se"
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Não tem conta? ",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = GreenDark
                                )
                            )
                            Text(
                                text = "Cadastre-se",
                                modifier = Modifier.clickable { onRegisterClick() },
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = GreenPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botão Entrar como Convidado - DESIGN MELHORADO
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.95f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Button(
                        onClick = onLoginSuccess,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = GreenPrimary
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Convidado",
                                modifier = Modifier.size(24.dp),
                                tint = GreenPrimary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Entrar como Convidado",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Dica de login para teste
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "💡 Para testar o app",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Use qualquer e-mail válido e senha com 3+ caracteres",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White.copy(alpha = 0.9f)
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Exemplo: user@teste.com / 123456",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White.copy(alpha = 0.8f),
                                fontWeight = FontWeight.Medium
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
