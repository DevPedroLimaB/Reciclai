package com.example.reciclai.network

import retrofit2.Response
import retrofit2.http.*
import com.example.reciclai.model.*

interface ReciclaiApiService {
    
    // Auth endpoints
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
    
    @POST("auth/social-login")
    suspend fun socialLogin(@Body request: SocialLoginRequest): Response<AuthResponse>
    
    @POST("auth/refresh")
    suspend fun refreshToken(@Header("Authorization") token: String): Response<AuthResponse>
    
    @POST("auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<ApiResponse<Any>>
    
    // User endpoints
    @GET("users/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): Response<ApiResponse<User>>
    
    @PUT("users/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body user: User
    ): Response<ApiResponse<User>>
    
    @GET("users/history")
    suspend fun getRecyclingHistory(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ListResponse<RecyclingHistory>>
    
    // Collect points endpoints
    @GET("collect-points")
    suspend fun getCollectPoints(
        @Query("lat") latitude: Double? = null,
        @Query("lng") longitude: Double? = null,
        @Query("radius") radius: Double? = null
    ): Response<ListResponse<CollectPoint>>
    
    @GET("collect-points/{id}")
    suspend fun getCollectPoint(@Path("id") id: String): Response<ApiResponse<CollectPoint>>
    
    // Schedule endpoints
    @GET("schedules")
    suspend fun getUserSchedules(
        @Header("Authorization") token: String,
        @Query("status") status: String? = null
    ): Response<ListResponse<Schedule>>
    
    @POST("schedules")
    suspend fun createSchedule(
        @Header("Authorization") token: String,
        @Body request: CreateScheduleRequest
    ): Response<ApiResponse<Schedule>>
    
    @PUT("schedules/{id}")
    suspend fun updateSchedule(
        @Header("Authorization") token: String,
        @Path("id") scheduleId: String,
        @Body request: UpdateScheduleRequest
    ): Response<ApiResponse<Schedule>>
    
    @DELETE("schedules/{id}")
    suspend fun cancelSchedule(
        @Header("Authorization") token: String,
        @Path("id") scheduleId: String
    ): Response<ApiResponse<Any>>
    
    // Rewards endpoints
    @GET("rewards")
    suspend fun getRewards(
        @Query("category") category: String? = null,
        @Query("available") available: Boolean? = null
    ): Response<ListResponse<Reward>>
    
    @POST("rewards/redeem")
    suspend fun redeemReward(
        @Header("Authorization") token: String,
        @Body request: RedeemRewardRequest
    ): Response<ApiResponse<UserReward>>
    
    @GET("rewards/user")
    suspend fun getUserRewards(
        @Header("Authorization") token: String,
        @Query("used") used: Boolean? = null
    ): Response<ListResponse<UserReward>>
    
    // Content endpoints
    @GET("content")
    suspend fun getContent(
        @Query("category") category: String? = null,
        @Query("tags") tags: String? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<ListResponse<Content>>
    
    @GET("content/{id}")
    suspend fun getContentById(@Path("id") id: String): Response<ApiResponse<Content>>
    
    // Community endpoints
    @GET("community/leaderboard")
    suspend fun getLeaderboard(
        @Query("period") period: String = "monthly", // weekly, monthly, yearly
        @Query("limit") limit: Int = 100
    ): Response<ListResponse<LeaderboardEntry>>
    
    @GET("community/challenges")
    suspend fun getChallenges(
        @Query("active") active: Boolean? = null
    ): Response<ListResponse<CommunityChallenge>>
    
    @POST("community/challenges/{id}/join")
    suspend fun joinChallenge(
        @Header("Authorization") token: String,
        @Path("id") challengeId: String
    ): Response<ApiResponse<Any>>
    
    // Material scanning
    @POST("materials/scan")
    suspend fun scanMaterial(
        @Header("Authorization") token: String,
        @Body request: ScanMaterialRequest
    ): Response<ApiResponse<MaterialInfo>>
    
    // Statistics
    @GET("users/stats")
    suspend fun getUserStats(
        @Header("Authorization") token: String
    ): Response<ApiResponse<UserStats>>
}

data class UserStats(
    val totalPoints: Int,
    val totalRecycled: Double,
    val co2Saved: Double,
    val schedulesCompleted: Int,
    val rewardsRedeemed: Int,
    val currentLevel: Int,
    val nextLevelPoints: Int,
    val monthlyStats: MonthlyStats
)

data class MonthlyStats(
    val points: Int,
    val recycled: Double,
    val schedules: Int
)