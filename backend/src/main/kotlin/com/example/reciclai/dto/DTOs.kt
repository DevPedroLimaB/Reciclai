package com.example.reciclai.dto

import java.util.UUID

// Auth DTOs
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

// User DTOs
data class UserResponse(
    val id: String,
    val name: String,
    val email: String,
    val phone: String?,
    val profileImageUrl: String?,
    val points: Int,
    val level: Int,
    val totalRecycled: Double,
    val co2Saved: Double,
    val visitedPoints: Int,
    val createdAt: String
)

// Recycling Point DTOs
data class RecyclingPointRequest(
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val acceptedMaterials: List<String>,
    val schedule: String,
    val phone: String? = null,
    val description: String? = null
)

data class RecyclingPointResponse(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val acceptedMaterials: List<String>,
    val schedule: String,
    val phone: String?,
    val description: String?,
    val rating: Float,
    val isActive: Boolean,
    val isVerified: Boolean,
    val createdBy: String?,
    val createdAt: String
)

// Content DTOs
data class ContentResponse(
    val id: String,
    val title: String,
    val summary: String,
    val content: String,
    val category: String,
    val author: String?,
    val readTime: Int,
    val imageUrl: String?,
    val tags: List<String>,
    val createdAt: String
)

// Generic Response
data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null
)

