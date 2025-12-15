package com.example.reciclai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade de Ponto de Reciclagem para cache local
 */
@Entity(tableName = "recycling_points")
data class RecyclingPointEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val phone: String? = null,
    val schedule: String,
    val description: String? = null,
    val acceptedMaterials: String, // JSON string
    val isVerified: Boolean = false,
    val rating: Double = 0.0,
    val cachedAt: Long = System.currentTimeMillis()
)

