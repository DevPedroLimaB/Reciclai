package com.example.reciclai.repository

import com.example.reciclai.data.local.dao.UserDao
import com.example.reciclai.data.local.entity.UserEntity
import com.example.reciclai.data.model.LoginResponse
import com.example.reciclai.shared.model.User
import com.example.reciclai.shared.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositório local de autenticação com banco de dados Room
 * Gerencia login, registro e persistência de sessão
 */
@Singleton
class LocalAuthRepository @Inject constructor(
    private val userDao: UserDao
) {

    /**
     * Verifica se há usuário logado
     */
    suspend fun isLoggedIn(): Boolean {
        return userDao.getLoggedInUser() != null
    }

    /**
     * Obtém o usuário logado
     */
    suspend fun getLoggedInUser(): User? {
        return userDao.getLoggedInUser()?.toUser()
    }

    /**
     * Faz login com credenciais
     */
    suspend fun login(email: String, password: String): Resource<LoginResponse> {
        return try {
            // Busca usuário no banco local
            val user = userDao.getUserByCredentials(email, password)

            if (user != null) {
                // Marca como logado
                userDao.logoutAllUsers()
                userDao.markUserAsLoggedIn(user.id)

                Resource.Success(
                    LoginResponse(
                        token = "local_token_${user.id}",
                        user = user.toUser()
                    )
                )
            } else {
                Resource.Error("Email ou senha inválidos")
            }
        } catch (e: Exception) {
            Resource.Error("Erro ao fazer login: ${e.message}")
        }
    }

    /**
     * Registra novo usuário
     */
    suspend fun register(
        name: String,
        email: String,
        password: String,
        phone: String? = null
    ): Resource<LoginResponse> {
        return try {
            // Verifica se email já existe
            val existingUser = userDao.getUserByEmail(email)
            if (existingUser != null) {
                return Resource.Error("Email já cadastrado")
            }

            // Cria novo usuário
            val newUser = UserEntity(
                id = UUID.randomUUID().toString(),
                name = name,
                email = email,
                password = password, // Em produção, usar hash
                phone = phone,
                points = 0,
                level = 1,
                totalRecycled = 0.0,
                co2Saved = 0.0,
                visitedPoints = 0,
                createdAt = System.currentTimeMillis().toString(),
                isLoggedIn = true
            )

            // Desloga outros usuários e insere novo
            userDao.logoutAllUsers()
            userDao.insertUser(newUser)

            Resource.Success(
                LoginResponse(
                    token = "local_token_${newUser.id}",
                    user = newUser.toUser()
                )
            )
        } catch (e: Exception) {
            Resource.Error("Erro ao registrar: ${e.message}")
        }
    }

    /**
     * Faz logout
     */
    suspend fun logout() {
        try {
            userDao.logoutAllUsers()
        } catch (_: Exception) {
            // Log error silently
        }
    }
}

/**
 * Extensões para converter entre Entity e Model
 */
private fun UserEntity.toUser(): User {
    return User(
        id = id,
        name = name,
        email = email,
        points = points,
        level = level,
        totalRecycled = totalRecycled,
        co2Saved = co2Saved,
        visitedPoints = visitedPoints,
        createdAt = createdAt
    )
}
