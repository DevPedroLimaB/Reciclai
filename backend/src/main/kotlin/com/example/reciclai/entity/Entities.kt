package com.example.reciclai.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(name = "password_hash", nullable = false)
    var passwordHash: String,

    var phone: String? = null,

    @Column(name = "profile_image_url")
    var profileImageUrl: String? = null,

    @Column(nullable = false)
    var points: Int = 0,

    @Column(nullable = false)
    var level: Int = 1,

    @Column(name = "total_recycled", nullable = false)
    var totalRecycled: Double = 0.0,

    @Column(name = "co2_saved", nullable = false)
    var co2Saved: Double = 0.0,

    @Column(name = "visited_points", nullable = false)
    var visitedPoints: Int = 0,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
)

@Entity
@Table(name = "recycling_points")
data class RecyclingPoint(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var address: String,

    @Column(nullable = false)
    var latitude: Double,

    @Column(nullable = false)
    var longitude: Double,

    @Column(name = "accepted_materials", nullable = false, columnDefinition = "TEXT[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    var acceptedMaterials: List<String> = emptyList(),

    @Column(nullable = false)
    var schedule: String,

    var phone: String? = null,

    var description: String? = null,

    @Column(nullable = false)
    var rating: Float = 0f,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @Column(name = "is_verified", nullable = false)
    var isVerified: Boolean = false,

    @Column(name = "created_by")
    var createdBy: UUID? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
)

@Entity
@Table(name = "contents")
data class Content(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var summary: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String,

    @Column(nullable = false)
    var category: String,

    var author: String? = null,

    @Column(name = "read_time", nullable = false)
    var readTime: Int = 5,

    @Column(name = "image_url")
    var imageUrl: String? = null,

    @Column(name = "tags", columnDefinition = "TEXT[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    var tags: List<String> = emptyList(),

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
)
