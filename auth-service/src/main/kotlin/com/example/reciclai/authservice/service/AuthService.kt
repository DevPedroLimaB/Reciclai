package com.example.reciclai.authservice.service

import com.example.reciclai.authservice.dto.*
import com.example.reciclai.authservice.model.User
import com.example.reciclai.authservice.repository.UserRepository
import com.example.reciclai.authservice.security.JwtTokenProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    fun login(request: LoginRequest): AuthResponse {
        val user = userRepository.findByEmail(request.email)
            .orElseThrow { Exception("Usuário não encontrado") }

        if (!passwordEncoder.matches(request.password, user.passwordHash)) {
            throw Exception("Senha incorreta")
        }

        val token = jwtTokenProvider.generateToken(user.id!!, user.email)

        return AuthResponse(
            token = token,
            user = UserResponse(
                id = user.id,
                name = user.name,
                email = user.email,
                points = user.points,
                level = user.level
            )
        )
    }

    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw Exception("Email já cadastrado")
        }

        val user = User(
            name = request.name,
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password),
            phone = request.phone,
            points = 0,
            level = 1
        )

        val savedUser = userRepository.save(user)
        val token = jwtTokenProvider.generateToken(savedUser.id!!, savedUser.email)

        return AuthResponse(
            token = token,
            user = UserResponse(
                id = savedUser.id,
                name = savedUser.name,
                email = savedUser.email,
                points = savedUser.points,
                level = savedUser.level
            )
        )
    }

    fun getCurrentUser(userId: String): UserResponse {
        val user = userRepository.findById(UUID.fromString(userId))
            .orElseThrow { Exception("Usuário não encontrado") }

        return UserResponse(
            id = user.id!!,
            name = user.name,
            email = user.email,
            points = user.points,
            level = user.level
        )
    }
}
