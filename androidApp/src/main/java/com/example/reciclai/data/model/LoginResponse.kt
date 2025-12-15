package com.example.reciclai.data.model

import com.example.reciclai.shared.model.User

/**
 * Resposta de login local
 */
data class LoginResponse(
    val token: String,
    val user: User
)

