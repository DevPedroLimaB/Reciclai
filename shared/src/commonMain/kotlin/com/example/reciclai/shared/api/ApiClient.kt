package com.example.reciclai.shared.api

import com.example.reciclai.shared.model.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// API Base URL
expect val API_BASE_URL: String

// DTOs compartilhados
@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val phone: String? = null
)

@Serializable
data class AuthResponse(
    val token: String,
    val user: User
)

@Serializable
data class RecyclingPointRequest(
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val acceptedMaterials: List<String>,
    val schedule: String,
    val phone: String? = null,
    val description: String? = null
)

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null
)

// Cliente API
class ReciclaiApiClient(private val httpClient: HttpClient) {

    private var authToken: String? = null

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun setAuthToken(token: String?) {
        authToken = token
    }

    // Auth endpoints
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val response = httpClient.post("$API_BASE_URL/api/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(email, password))
            }

            val apiResponse = json.decodeFromString<ApiResponse<AuthResponse>>(response.bodyAsText())
            if (apiResponse.success && apiResponse.data != null) {
                authToken = apiResponse.data.token
                Result.success(apiResponse.data)
            } else {
                Result.failure(Exception(apiResponse.message ?: "Login falhou"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(name: String, email: String, password: String, phone: String? = null): Result<AuthResponse> {
        return try {
            val response = httpClient.post("$API_BASE_URL/api/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(RegisterRequest(name, email, password, phone))
            }

            val apiResponse = json.decodeFromString<ApiResponse<AuthResponse>>(response.bodyAsText())
            if (apiResponse.success && apiResponse.data != null) {
                authToken = apiResponse.data.token
                Result.success(apiResponse.data)
            } else {
                Result.failure(Exception(apiResponse.message ?: "Registro falhou"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCurrentUser(): Result<User> {
        return try {
            val response = httpClient.get("$API_BASE_URL/api/auth/me") {
                header("Authorization", "Bearer $authToken")
            }

            val apiResponse = json.decodeFromString<ApiResponse<User>>(response.bodyAsText())
            if (apiResponse.success && apiResponse.data != null) {
                Result.success(apiResponse.data)
            } else {
                Result.failure(Exception(apiResponse.message ?: "Erro ao buscar usuário"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Recycling Points endpoints
    suspend fun getAllRecyclingPoints(): Result<List<RecyclingPoint>> {
        return try {
            val response = httpClient.get("$API_BASE_URL/api/recycling-points/all")

            val apiResponse = json.decodeFromString<ApiResponse<List<RecyclingPoint>>>(response.bodyAsText())
            if (apiResponse.success && apiResponse.data != null) {
                Result.success(apiResponse.data)
            } else {
                Result.failure(Exception(apiResponse.message ?: "Erro ao buscar pontos"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRecyclingPointById(id: String): Result<RecyclingPoint> {
        return try {
            val response = httpClient.get("$API_BASE_URL/api/recycling-points/$id")

            val apiResponse = json.decodeFromString<ApiResponse<RecyclingPoint>>(response.bodyAsText())
            if (apiResponse.success && apiResponse.data != null) {
                Result.success(apiResponse.data)
            } else {
                Result.failure(Exception(apiResponse.message ?: "Ponto não encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createRecyclingPoint(
        name: String,
        address: String,
        latitude: Double,
        longitude: Double,
        acceptedMaterials: List<String>,
        schedule: String,
        phone: String? = null,
        description: String? = null
    ): Result<RecyclingPoint> {
        return try {
            val response = httpClient.post("$API_BASE_URL/api/recycling-points") {
                header("Authorization", "Bearer $authToken")
                contentType(ContentType.Application.Json)
                setBody(RecyclingPointRequest(name, address, latitude, longitude, acceptedMaterials, schedule, phone, description))
            }

            val apiResponse = json.decodeFromString<ApiResponse<RecyclingPoint>>(response.bodyAsText())
            if (apiResponse.success && apiResponse.data != null) {
                Result.success(apiResponse.data)
            } else {
                Result.failure(Exception(apiResponse.message ?: "Erro ao criar ponto"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Contents endpoints
    suspend fun getAllContents(): Result<List<Content>> {
        return try {
            val response = httpClient.get("$API_BASE_URL/api/contents")

            val apiResponse = json.decodeFromString<ApiResponse<List<Content>>>(response.bodyAsText())
            if (apiResponse.success && apiResponse.data != null) {
                Result.success(apiResponse.data)
            } else {
                Result.failure(Exception(apiResponse.message ?: "Erro ao buscar conteúdos"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getContentById(id: String): Result<Content> {
        return try {
            val response = httpClient.get("$API_BASE_URL/api/contents/$id")

            val apiResponse = json.decodeFromString<ApiResponse<Content>>(response.bodyAsText())
            if (apiResponse.success && apiResponse.data != null) {
                Result.success(apiResponse.data)
            } else {
                Result.failure(Exception(apiResponse.message ?: "Conteúdo não encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

