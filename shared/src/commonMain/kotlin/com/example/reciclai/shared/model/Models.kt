package com.example.reciclai.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String? = null,
    val profileImageUrl: String? = null,
    val points: Int = 0,
    val level: Int = 1,
    val totalRecycled: Double = 0.0,
    val co2Saved: Double = 0.0,
    val visitedPoints: Int = 0,
    val createdAt: String,
    val updatedAt: String? = null
)

@Serializable
data class RecyclingPoint(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val acceptedMaterials: List<String>,
    val schedule: String,
    val phone: String? = null,
    val description: String? = null,
    val rating: Float = 0f,
    val isActive: Boolean = true,
    val isVerified: Boolean = false,
    val createdBy: String? = null,
    val createdAt: String = "",
    val updatedAt: String? = null
)

@Serializable
data class CollectPoint(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val acceptedMaterials: List<String>,
    val operatingHours: String,
    val phone: String? = null,
    val rating: Float = 0f,
    val isActive: Boolean = true,
    val contactInfo: String? = null
)

@Serializable
data class Content(
    val id: String,
    val title: String,
    val summary: String,
    val content: String,
    val category: String,
    val author: String? = null,
    val readTime: Int,
    val imageUrl: String? = null,
    val tags: List<String> = emptyList(),
    val createdAt: String,
    val updatedAt: String? = null
)

@Suppress("unused") // Será usada em funcionalidade futura de agendamentos
@Serializable
data class Schedule(
    val id: String,
    val userId: String,
    val collectPointId: String,
    val scheduledDate: String,
    val timeSlot: String,
    val materials: List<String>,
    val estimatedWeight: Double,
    val status: String,
    val notes: String? = null,
    val createdAt: String
)

@Serializable
data class Reward(
    val id: String,
    val title: String,
    val description: String,
    val pointsCost: Int,
    val category: String,
    val imageUrl: String? = null,
    val isAvailable: Boolean = true,
    val expiresAt: String? = null
)

@Suppress("unused") // Será usada em sistema de conquistas/gamificação futuro
@Serializable
data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val iconUrl: String? = null,
    val pointsReward: Int,
    val condition: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: String? = null
)

@Serializable
data class RecyclingHistory(
    val id: String,
    val userId: String,
    val collectPointId: String,
    val collectPointName: String,
    val materials: List<String>,
    val weight: Double,
    val pointsEarned: Int,
    val co2Saved: Double,
    val date: String,
    val status: String = "completed"
)

@Serializable
data class UserStats(
    val totalPoints: Int = 0,
    val totalRecycled: Double = 0.0,
    val co2Saved: Double = 0.0,
    val currentLevel: Int = 1,
    val schedulesCompleted: Int = 0,
    val rewardsRedeemed: Int = 0
)

@Suppress("unused") // Será usada em base de dados de materiais futura
@Serializable
data class MaterialInfo(
    val name: String,
    val category: String,
    val recyclable: Boolean,
    val description: String,
    val disposalInstructions: String,
    val pointsValue: Int = 0
)
