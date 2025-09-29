package com.example.reciclai.repository

import com.example.reciclai.model.*
import com.example.reciclai.network.*
import com.example.reciclai.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CollectPointRepositoryImpl @Inject constructor(
    private val apiService: ReciclaiApiService
) : CollectPointRepository {

    override suspend fun getCollectPoints(
        latitude: Double?,
        longitude: Double?,
        radius: Double?
    ): Resource<List<CollectPoint>> {
        return try {
            val response = apiService.getCollectPoints(latitude, longitude, radius)
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(response.body()!!.data)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to get collect points")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun getCollectPoint(id: String): Resource<CollectPoint> {
        return try {
            val response = apiService.getCollectPoint(id)
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(response.body()!!.data!!)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to get collect point")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }
}

class ScheduleRepositoryImpl @Inject constructor(
    private val apiService: ReciclaiApiService
) : ScheduleRepository {

    override suspend fun getUserSchedules(status: String?): Resource<List<Schedule>> {
        return try {
            val response = apiService.getUserSchedules("", status)
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(response.body()!!.data)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to get schedules")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun createSchedule(request: CreateScheduleRequest): Resource<Schedule> {
        return try {
            val response = apiService.createSchedule("", request)
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(response.body()!!.data!!)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to create schedule")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun updateSchedule(
        scheduleId: String,
        request: UpdateScheduleRequest
    ): Resource<Schedule> {
        return try {
            val response = apiService.updateSchedule("", scheduleId, request)
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(response.body()!!.data!!)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to update schedule")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun cancelSchedule(scheduleId: String): Resource<Unit> {
        return try {
            val response = apiService.cancelSchedule("", scheduleId)
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(Unit)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to cancel schedule")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }
}

class RewardRepositoryImpl @Inject constructor(
    private val apiService: ReciclaiApiService
) : RewardRepository {

    override suspend fun getRewards(category: String?, available: Boolean?): Resource<List<Reward>> {
        return try {
            val response = apiService.getRewards(category, available)
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(response.body()!!.data)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to get rewards")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun redeemReward(rewardId: String): Resource<UserReward> {
        return try {
            val response = apiService.redeemReward("", RedeemRewardRequest(rewardId))
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(response.body()!!.data!!)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to redeem reward")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun getUserRewards(used: Boolean?): Resource<List<UserReward>> {
        return try {
            val response = apiService.getUserRewards("", used)
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(response.body()!!.data)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to get user rewards")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }
}

class ContentRepositoryImpl @Inject constructor(
    private val apiService: ReciclaiApiService
) : ContentRepository {

    override suspend fun getContent(
        category: String?,
        tags: String?,
        page: Int,
        limit: Int
    ): Resource<List<Content>> {
        return try {
            val response = apiService.getContent(category, tags, page, limit)
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(response.body()!!.data)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to get content")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun getContentById(id: String): Resource<Content> {
        return try {
            val response = apiService.getContentById(id)
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(response.body()!!.data!!)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to get content")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }
}

class CommunityRepositoryImpl @Inject constructor(
    private val apiService: ReciclaiApiService
) : CommunityRepository {

    override suspend fun getLeaderboard(period: String, limit: Int): Resource<List<LeaderboardEntry>> {
        return try {
            val response = apiService.getLeaderboard(period, limit)
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(response.body()!!.data)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to get leaderboard")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun getChallenges(active: Boolean?): Resource<List<CommunityChallenge>> {
        return try {
            val response = apiService.getChallenges(active)
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(response.body()!!.data)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to get challenges")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun joinChallenge(challengeId: String): Resource<Unit> {
        return try {
            val response = apiService.joinChallenge("", challengeId)
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(Unit)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to join challenge")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }
}