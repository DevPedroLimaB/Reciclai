package com.example.reciclai.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val name: String,
    val email: String,
    val profileImageUrl: String? = null,
    val points: Int = 0,
    val level: Int = 1,
    val totalRecycled: Double = 0.0,
    val co2Saved: Double = 0.0,
    val createdAt: String
) : Parcelable

@Parcelize
data class CollectPoint(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val acceptedMaterials: List<String>,
    val operatingHours: String,
    val phone: String? = null,
    val isActive: Boolean = true
) : Parcelable

@Parcelize
data class Schedule(
    val id: String,
    val userId: String,
    val collectPointId: String,
    val collectPoint: CollectPoint? = null,
    val scheduledDate: String,
    val timeSlot: String,
    val materials: List<String>,
    val estimatedWeight: Double,
    val status: ScheduleStatus,
    val notes: String? = null,
    val createdAt: String
) : Parcelable

enum class ScheduleStatus {
    PENDING, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED
}

@Parcelize
data class Reward(
    val id: String,
    val title: String,
    val description: String,
    val pointsCost: Int,
    val imageUrl: String? = null,
    val category: String,
    val isAvailable: Boolean = true,
    val validUntil: String? = null
) : Parcelable

@Parcelize
data class UserReward(
    val id: String,
    val userId: String,
    val rewardId: String,
    val reward: Reward,
    val redeemedAt: String,
    val usedAt: String? = null,
    val couponCode: String,
    val isUsed: Boolean = false
) : Parcelable

@Parcelize
data class Content(
    val id: String,
    val title: String,
    val summary: String,
    val content: String,
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val category: String,
    val tags: List<String>,
    val publishedAt: String,
    val readTime: Int
) : Parcelable

@Parcelize
data class CommunityChallenge(
    val id: String,
    val title: String,
    val description: String,
    val target: Double,
    val current: Double,
    val unit: String,
    val startDate: String,
    val endDate: String,
    val reward: String,
    val participants: Int = 0,
    val isActive: Boolean = true
) : Parcelable

data class LeaderboardEntry(
    val userId: String,
    val userName: String,
    val userImageUrl: String? = null,
    val points: Int,
    val position: Int,
    val totalRecycled: Double
)

data class RecyclingHistory(
    val id: String,
    val userId: String,
    val scheduleId: String? = null,
    val materials: List<String>,
    val weight: Double,
    val pointsEarned: Int,
    val co2Saved: Double,
    val date: String,
    val collectPointName: String
)