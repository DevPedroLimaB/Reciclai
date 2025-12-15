package com.example.reciclai.authservice.dto

import java.util.UUID

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val phone: String? = null
)

data class AuthResponse(
    val token: String,
    val user: UserResponse
)

data class UserResponse(
    val id: UUID,
    val name: String,
    val email: String,
    val points: Int,
    val level: Int
)

data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null
)
