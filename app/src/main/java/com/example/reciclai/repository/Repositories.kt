package com.example.reciclai.repository

import com.example.reciclai.model.*
import com.example.reciclai.network.*
import com.example.reciclai.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<AuthData>
    suspend fun register(name: String, email: String, password: String): Resource<AuthData>
    suspend fun socialLogin(provider: String, token: String, name: String, email: String): Resource<AuthData>
    suspend fun refreshToken(): Resource<AuthData>
    suspend fun logout(): Resource<Unit>
    fun isLoggedIn(): Boolean
}

interface UserRepository {
    suspend fun getUserProfile(): Resource<User>
    suspend fun updateProfile(user: User): Resource<User>
    suspend fun getRecyclingHistory(page: Int, limit: Int): Resource<List<RecyclingHistory>>
    suspend fun getUserStats(): Resource<UserStats>
}

interface CollectPointRepository {
    suspend fun getCollectPoints(latitude: Double?, longitude: Double?, radius: Double?): Resource<List<CollectPoint>>
    suspend fun getCollectPoint(id: String): Resource<CollectPoint>
}

interface ScheduleRepository {
    suspend fun getUserSchedules(status: String?): Resource<List<Schedule>>
    suspend fun createSchedule(request: CreateScheduleRequest): Resource<Schedule>
    suspend fun updateSchedule(scheduleId: String, request: UpdateScheduleRequest): Resource<Schedule>
    suspend fun cancelSchedule(scheduleId: String): Resource<Unit>
}

interface RewardRepository {
    suspend fun getRewards(category: String?, available: Boolean?): Resource<List<Reward>>
    suspend fun redeemReward(rewardId: String): Resource<UserReward>
    suspend fun getUserRewards(used: Boolean?): Resource<List<UserReward>>
}

interface ContentRepository {
    suspend fun getContent(category: String?, tags: String?, page: Int, limit: Int): Resource<List<Content>>
    suspend fun getContentById(id: String): Resource<Content>
}

interface CommunityRepository {
    suspend fun getLeaderboard(period: String, limit: Int): Resource<List<LeaderboardEntry>>
    suspend fun getChallenges(active: Boolean?): Resource<List<CommunityChallenge>>
    suspend fun joinChallenge(challengeId: String): Resource<Unit>
}

interface MaterialScanRepository {
    suspend fun scanMaterial(barcode: String?, imageBase64: String?): Resource<MaterialInfo>
}