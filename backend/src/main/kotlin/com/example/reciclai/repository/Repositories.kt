package com.example.reciclai.repository

import com.example.reciclai.entity.User
import com.example.reciclai.entity.RecyclingPoint
import com.example.reciclai.entity.Content
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
}

@Repository
interface RecyclingPointRepository : JpaRepository<RecyclingPoint, UUID> {
    fun findByIsActiveTrue(): List<RecyclingPoint>
    fun findByCreatedBy(userId: UUID): List<RecyclingPoint>
}

@Repository
interface ContentRepository : JpaRepository<Content, UUID> {
    fun findByCategory(category: String): List<Content>
    fun findAllByOrderByCreatedAtDesc(): List<Content>
}

