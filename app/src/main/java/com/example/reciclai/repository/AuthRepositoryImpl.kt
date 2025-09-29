package com.example.reciclai.repository

import com.example.reciclai.data.local.UserDao
import com.example.reciclai.data.local.UserEntity
import com.example.reciclai.model.*
import com.example.reciclai.network.*
import com.example.reciclai.util.PreferencesManager
import com.example.reciclai.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ReciclaiApiService,
    private val preferencesManager: PreferencesManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): Resource<AuthData> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body()?.success == true) {
                val authData = response.body()!!.data!!
                saveAuthData(authData)
                Resource.Success(authData)
            } else {
                Resource.Error(response.body()?.message ?: "Login failed")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun register(name: String, email: String, password: String): Resource<AuthData> {
        return try {
            val response = apiService.register(RegisterRequest(name, email, password))
            if (response.isSuccessful && response.body()?.success == true) {
                val authData = response.body()!!.data!!
                saveAuthData(authData)
                Resource.Success(authData)
            } else {
                Resource.Error(response.body()?.message ?: "Registration failed")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun socialLogin(provider: String, token: String, name: String, email: String): Resource<AuthData> {
        return try {
            val response = apiService.socialLogin(SocialLoginRequest(provider, token, name, email))
            if (response.isSuccessful && response.body()?.success == true) {
                val authData = response.body()!!.data!!
                saveAuthData(authData)
                Resource.Success(authData)
            } else {
                Resource.Error(response.body()?.message ?: "Social login failed")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun refreshToken(): Resource<AuthData> {
        return try {
            val refreshToken = preferencesManager.getRefreshToken()
                ?: return Resource.Error("No refresh token available")
            
            val response = apiService.refreshToken("Bearer $refreshToken")
            if (response.isSuccessful && response.body()?.success == true) {
                val authData = response.body()!!.data!!
                saveAuthData(authData)
                Resource.Success(authData)
            } else {
                Resource.Error(response.body()?.message ?: "Token refresh failed")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun logout(): Resource<Unit> {
        return try {
            val token = preferencesManager.getAuthToken()
            if (token != null) {
                apiService.logout("Bearer $token")
            }
            preferencesManager.clearUserData()
            Resource.Success(Unit)
        } catch (e: Exception) {
            // Clear local data even if server request fails
            preferencesManager.clearUserData()
            Resource.Success(Unit)
        }
    }

    override fun isLoggedIn(): Boolean = preferencesManager.isLoggedIn()

    private fun saveAuthData(authData: AuthData) {
        preferencesManager.setAuthToken(authData.token)
        preferencesManager.setRefreshToken(authData.refreshToken)
        preferencesManager.setUserId(authData.user.id)
    }
}

class UserRepositoryImpl @Inject constructor(
    private val apiService: ReciclaiApiService,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUserProfile(): Resource<User> {
        return try {
            val response = apiService.getUserProfile("")
            if (response.isSuccessful && response.body()?.success == true) {
                val user = response.body()!!.data!!
                // Cache user locally
                userDao.insertUser(user.toEntity())
                Resource.Success(user)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to get user profile")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun updateProfile(user: User): Resource<User> {
        return try {
            val response = apiService.updateProfile("", user)
            if (response.isSuccessful && response.body()?.success == true) {
                val updatedUser = response.body()!!.data!!
                userDao.updateUser(updatedUser.toEntity())
                Resource.Success(updatedUser)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to update profile")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun getRecyclingHistory(page: Int, limit: Int): Resource<List<RecyclingHistory>> {
        return try {
            val response = apiService.getRecyclingHistory("", page, limit)
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(response.body()!!.data)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to get recycling history")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Connection error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    override suspend fun getUserStats(): Resource<UserStats> {
        return try {
            val response = apiService.getUserStats("")
            if (response.isSuccessful && response.body()?.success == true) {
                Resource.Success(response.body()!!.data!!)
            } else {
                Resource.Error(response.body()?.message ?: "Failed to get user stats")
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

private fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        profileImageUrl = profileImageUrl,
        points = points,
        level = level,
        totalRecycled = totalRecycled,
        co2Saved = co2Saved,
        createdAt = createdAt
    )
}