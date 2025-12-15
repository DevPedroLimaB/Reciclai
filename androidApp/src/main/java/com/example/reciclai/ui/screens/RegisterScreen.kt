package com.example.reciclai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reciclai.shared.util.Resource
import com.example.reciclai.ui.theme.*
import com.example.reciclai.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var acceptTerms by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val authState by authViewModel.authState.collectAsState()
    val focusManager = LocalFocusManager.current

    // Validações
    val isEmailValid = email.contains("@") && email.contains(".")
    val isPasswordValid = password.length >= 6
    val doPasswordsMatch = password == confirmPassword
    val isFormValid = name.isNotBlank() && isEmailValid && isPasswordValid &&
                     doPasswordsMatch && acceptTerms

    LaunchedEffect(authState) {
        when (authState) {
            is Resource.Loading -> {
                isLoading = true
            }
            is Resource.Success -> {
                isLoading = false
                onRegisterSuccess()
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // Logo
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

                // Títulos
                Text(
                    text = "Criar Conta",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Junte-se à comunidade sustentável",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White.copy(alpha = 0.9f)
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Formulário
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
                        // Campo Nome
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Nome completo") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Person,
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

                        // Campo Email
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
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
                                focusedBorderColor = GreenPrimary,
                                focusedLabelColor = GreenPrimary,
                                cursorColor = GreenPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            isError = email.isNotBlank() && !isEmailValid
                        )

                        if (email.isNotBlank() && !isEmailValid) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Email inválido",
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
                            onValueChange = { password = it },
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
                            shape = RoundedCornerShape(12.dp),
                            isError = password.isNotBlank() && !isPasswordValid
                        )

                        if (password.isNotBlank() && !isPasswordValid) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Senha deve ter pelo menos 6 caracteres",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFFD32F2F)
                                ),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Campo Confirmar Senha
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text("Confirmar senha") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = "Confirmar senha",
                                    tint = GreenPrimary
                                )
                            },
                            trailingIcon = {
                                Row {
                                    if (confirmPassword.isNotBlank()) {
                                        Icon(
                                            if (doPasswordsMatch) Icons.Default.Check else Icons.Default.Close,
                                            contentDescription = if (doPasswordsMatch) "Senhas coincidem" else "Senhas não coincidem",
                                            tint = if (doPasswordsMatch) GreenPrimary else Color(0xFFD32F2F),
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                        Icon(
                                            imageVector = Icons.Default.RemoveRedEye,
                                            contentDescription = if (confirmPasswordVisible) "Ocultar senha" else "Mostrar senha",
                                            tint = if (confirmPasswordVisible) GreenPrimary else GreenPrimary.copy(alpha = 0.6f)
                                        )
                                    }
                                }
                            },
                            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
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
                            shape = RoundedCornerShape(12.dp),
                            isError = confirmPassword.isNotBlank() && !doPasswordsMatch
                        )

                        if (confirmPassword.isNotBlank() && !doPasswordsMatch) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Senhas não coincidem",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFFD32F2F)
                                ),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Checkbox de termos
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = acceptTerms,
                                onCheckedChange = { acceptTerms = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = GreenPrimary,
                                    uncheckedColor = GreenDark.copy(alpha = 0.6f)
                                )
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Column {
                                Text(
                                    text = "Eu aceito os ",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = GreenDark.copy(alpha = 0.8f)
                                    )
                                )
                                Text(
                                    text = "Termos de Uso e Política de Privacidade",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = GreenPrimary,
                                        fontWeight = FontWeight.Medium
                                    ),
                                    modifier = Modifier.clickable {
                                        // TODO: Abrir termos
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Botão Cadastrar
                        Button(
                            onClick = {
                                if (isFormValid) {
                                    authViewModel.register(name, email, password)
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
                                Text(
                                    text = "Criar Conta",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Link para login
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Já tem conta? ",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = GreenDark.copy(alpha = 0.7f)
                                )
                            )
                            Text(
                                text = "Faça login",
                                modifier = Modifier.clickable { onLoginClick() },
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = GreenPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
