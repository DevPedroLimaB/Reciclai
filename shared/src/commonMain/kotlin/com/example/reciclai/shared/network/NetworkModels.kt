package com.example.reciclai.shared.network

import com.example.reciclai.shared.model.User
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val phone: String? = null
)

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
)

@Serializable
data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)

@Serializable
data class CreateScheduleRequest(
    val collectPointId: String,
    val scheduledDate: String,
    val timeSlot: String,
    val materials: List<String>,
    val estimatedWeight: Double,
    val notes: String? = null
)

@Serializable
data class UpdateScheduleRequest(
    val scheduledDate: String? = null,
    val timeSlot: String? = null,
    val materials: List<String>? = null,
    val estimatedWeight: Double? = null,
    val notes: String? = null,
    val status: String? = null
)

@Serializable
data class ScanMaterialRequest(
    val barcode: String? = null,
    val imageBase64: String? = null
)
