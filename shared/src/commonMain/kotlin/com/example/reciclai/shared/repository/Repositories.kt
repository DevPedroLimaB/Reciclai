package com.example.reciclai.shared.repository

import com.example.reciclai.shared.model.*
import com.example.reciclai.shared.network.*
import com.example.reciclai.shared.util.Resource
import com.example.reciclai.shared.storage.StorageService

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<AuthResponse>
    suspend fun register(name: String, email: String, password: String, phone: String?): Resource<AuthResponse>
    suspend fun logout()
    fun isLoggedIn(): Boolean
    fun getAuthToken(): String?
    fun saveAuthToken(token: String)
}

interface UserRepository {
    suspend fun getUserProfile(): Resource<User>
    suspend fun getUserStats(): Resource<UserStats>
    suspend fun getRecyclingHistory(page: Int = 0, limit: Int = 20): Resource<List<RecyclingHistory>>
}

interface ContentRepository {
    suspend fun getContent(category: String?, page: Int, limit: Int): Resource<List<Content>>
}

interface CollectPointRepository {
    suspend fun getCollectPoints(latitude: Double?, longitude: Double?): Resource<List<CollectPoint>>
}

interface RecyclingRepository {
    suspend fun getRecyclingPoints(latitude: Double?, longitude: Double?): Resource<List<RecyclingPoint>>
    suspend fun addRecyclingPoint(point: RecyclingPoint): Resource<RecyclingPoint>
    suspend fun updateRecyclingPoint(point: RecyclingPoint): Resource<RecyclingPoint>
    suspend fun deleteRecyclingPoint(pointId: String): Resource<Boolean>
    suspend fun getRecyclingPointById(id: String): Resource<RecyclingPoint>
    suspend fun searchRecyclingPoints(query: String, materials: List<String>?): Resource<List<RecyclingPoint>>
}

interface RewardRepository {
    suspend fun getRewards(): Resource<List<Reward>>
}

// Implementações
class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val storage: StorageService
) : AuthRepository {

    override suspend fun login(email: String, password: String): Resource<AuthResponse> {
        return try {
            // Simula autenticação bem-sucedida para demonstração
            val mockUser = User(
                id = "user_123",
                name = "Usuário ReciclAI",
                email = email,
                points = 230,
                level = 3,
                totalRecycled = 47.5,
                co2Saved = 12.3,
                visitedPoints = 12,
                createdAt = "2024-01-01T00:00:00Z"
            )

            val response = AuthResponse(
                accessToken = "mock_token_abc123",
                refreshToken = "refresh_token",
                user = mockUser
            )

            saveAuthToken(response.accessToken)
            storage.setLoggedIn(true)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Erro no login: ${e.message}")
        }
    }

    override suspend fun register(name: String, email: String, password: String, phone: String?): Resource<AuthResponse> {
        return try {
            // Simula registro bem-sucedido para demonstração
            val newUser = User(
                id = "user_new_${email.hashCode()}",
                name = name,
                email = email,
                phone = phone,
                points = 0,
                level = 1,
                totalRecycled = 0.0,
                co2Saved = 0.0,
                visitedPoints = 0,
                createdAt = "2024-01-01T00:00:00Z"
            )

            val response = AuthResponse(
                accessToken = "mock_token_${email.hashCode()}",
                refreshToken = "refresh_token",
                user = newUser
            )

            saveAuthToken(response.accessToken)
            storage.setLoggedIn(true)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Erro no registro: ${e.message}")
        }
    }

    override suspend fun logout() {
        storage.setLoggedIn(false)
        storage.clear()
    }

    override fun isLoggedIn(): Boolean {
        return storage.isLoggedIn()
    }

    override fun getAuthToken(): String? {
        return storage.getAuthToken()
    }

    override fun saveAuthToken(token: String) {
        storage.saveAuthToken(token)
    }
}

class UserRepositoryImpl(
    private val apiService: ApiService
) : UserRepository {
    override suspend fun getUserProfile(): Resource<User> {
        return Resource.Loading()
    }

    override suspend fun getUserStats(): Resource<UserStats> {
        return Resource.Loading()
    }

    override suspend fun getRecyclingHistory(page: Int, limit: Int): Resource<List<RecyclingHistory>> {
        return Resource.Loading()
    }
}

class ContentRepositoryImpl(
    private val apiService: ApiService
) : ContentRepository {
    override suspend fun getContent(category: String?, page: Int, limit: Int): Resource<List<Content>> {
        return Resource.Loading()
    }
}

class CollectPointRepositoryImpl(
    private val apiService: ApiService
) : CollectPointRepository {
    override suspend fun getCollectPoints(latitude: Double?, longitude: Double?): Resource<List<CollectPoint>> {
        return Resource.Loading()
    }
}

class RecyclingRepositoryImpl(
    private val apiService: ApiService
) : RecyclingRepository {
    override suspend fun getRecyclingPoints(latitude: Double?, longitude: Double?): Resource<List<RecyclingPoint>> {
        return Resource.Loading()
    }

    override suspend fun addRecyclingPoint(point: RecyclingPoint): Resource<RecyclingPoint> {
        return Resource.Loading()
    }

    override suspend fun updateRecyclingPoint(point: RecyclingPoint): Resource<RecyclingPoint> {
        return Resource.Loading()
    }

    override suspend fun deleteRecyclingPoint(pointId: String): Resource<Boolean> {
        return Resource.Loading()
    }

    override suspend fun getRecyclingPointById(id: String): Resource<RecyclingPoint> {
        return Resource.Loading()
    }

    override suspend fun searchRecyclingPoints(query: String, materials: List<String>?): Resource<List<RecyclingPoint>> {
        return Resource.Loading()
    }
}

class RewardRepositoryImpl(
    private val apiService: ApiService
) : RewardRepository {
    override suspend fun getRewards(): Resource<List<Reward>> {
        return Resource.Loading()
    }
}
