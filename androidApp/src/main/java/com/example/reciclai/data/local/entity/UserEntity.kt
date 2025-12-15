package com.example.reciclai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade de Usuário para persistência local
 * Armazena credenciais e dados do usuário logado
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val password: String? = null, // Apenas para demo - em produção usar hash
    val phone: String? = null,
    val points: Int = 0,
    val level: Int = 1,
    val totalRecycled: Double = 0.0,
    val co2Saved: Double = 0.0,
    val visitedPoints: Int = 0,
    val createdAt: String,
    val isLoggedIn: Boolean = false
)

