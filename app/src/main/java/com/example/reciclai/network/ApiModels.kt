package com.example.reciclai.network

// Auth requests/responses
data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class SocialLoginRequest(
    val provider: String, // "google" or "facebook"
    val token: String,
    val name: String,
    val email: String
)

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val data: AuthData? = null
)

data class AuthData(
    val user: com.example.reciclai.model.User,
    val token: String,
    val refreshToken: String
)

// Schedule requests
data class CreateScheduleRequest(
    val collectPointId: String,
    val scheduledDate: String,
    val timeSlot: String,
    val materials: List<String>,
    val estimatedWeight: Double,
    val notes: String? = null
)

data class UpdateScheduleRequest(
    val scheduledDate: String? = null,
    val timeSlot: String? = null,
    val materials: List<String>? = null,
    val estimatedWeight: Double? = null,
    val notes: String? = null
)

// Reward redemption
data class RedeemRewardRequest(
    val rewardId: String
)

// Generic API responses
data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)

data class ListResponse<T>(
    val success: Boolean,
    val message: String,
    val data: List<T>,
    val total: Int,
    val page: Int,
    val limit: Int
)

// Material scanning
data class ScanMaterialRequest(
    val barcode: String? = null,
    val imageBase64: String? = null
)

data class MaterialInfo(
    val name: String,
    val category: String,
    val recyclable: Boolean,
    val instructions: String,
    val pointsValue: Int
)