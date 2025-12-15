package com.example.reciclai.service

import com.example.reciclai.dto.*
import com.example.reciclai.entity.User
import com.example.reciclai.entity.RecyclingPoint
import com.example.reciclai.entity.Content
import com.example.reciclai.repository.UserRepository
import com.example.reciclai.repository.RecyclingPointRepository
import com.example.reciclai.repository.ContentRepository
import com.example.reciclai.security.JwtTokenProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    fun login(request: LoginRequest): AuthResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("Email ou senha inválidos")

        if (!passwordEncoder.matches(request.password, user.passwordHash)) {
            throw IllegalArgumentException("Email ou senha inválidos")
        }

        val token = jwtTokenProvider.generateToken(user.id.toString())
        return AuthResponse(token, user.toResponse())
    }

    @Transactional
    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email já cadastrado")
        }

        val user = User(
            name = request.name,
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password),
            phone = request.phone
        )

        val savedUser = userRepository.save(user)
        val token = jwtTokenProvider.generateToken(savedUser.id.toString())

        return AuthResponse(token, savedUser.toResponse())
    }

    fun getCurrentUser(userId: String): UserResponse {
        val user = userRepository.findById(UUID.fromString(userId))
            .orElseThrow { IllegalArgumentException("Usuário não encontrado") }
        return user.toResponse()
    }
}

@Service
class RecyclingPointService(
    private val recyclingPointRepository: RecyclingPointRepository
) {

    fun getAllPoints(): List<RecyclingPointResponse> {
        return recyclingPointRepository.findByIsActiveTrue()
            .map { it.toResponse() }
    }

    fun getPointById(id: String): RecyclingPointResponse {
        val point = recyclingPointRepository.findById(UUID.fromString(id))
            .orElseThrow { IllegalArgumentException("Ponto não encontrado") }
        return point.toResponse()
    }

    @Transactional
    fun createPoint(request: RecyclingPointRequest, userId: String): RecyclingPointResponse {
        val point = RecyclingPoint(
            name = request.name,
            address = request.address,
            latitude = request.latitude,
            longitude = request.longitude,
            acceptedMaterials = request.acceptedMaterials,
            schedule = request.schedule,
            phone = request.phone,
            description = request.description,
            createdBy = UUID.fromString(userId)
        )

        val savedPoint = recyclingPointRepository.save(point)
        return savedPoint.toResponse()
    }

    @Transactional
    fun updatePoint(id: String, request: RecyclingPointRequest): RecyclingPointResponse {
        val point = recyclingPointRepository.findById(UUID.fromString(id))
            .orElseThrow { IllegalArgumentException("Ponto não encontrado") }

        point.name = request.name
        point.address = request.address
        point.latitude = request.latitude
        point.longitude = request.longitude
        point.acceptedMaterials = request.acceptedMaterials
        point.schedule = request.schedule
        point.phone = request.phone
        point.description = request.description

        val updatedPoint = recyclingPointRepository.save(point)
        return updatedPoint.toResponse()
    }

    @Transactional
    fun deletePoint(id: String) {
        val point = recyclingPointRepository.findById(UUID.fromString(id))
            .orElseThrow { IllegalArgumentException("Ponto não encontrado") }
        point.isActive = false
        recyclingPointRepository.save(point)
    }
}

@Service
class ContentService(
    private val contentRepository: ContentRepository
) {

    fun getAllContents(): List<ContentResponse> {
        return contentRepository.findAllByOrderByCreatedAtDesc()
            .map { it.toResponse() }
    }

    fun getContentById(id: String): ContentResponse {
        val content = contentRepository.findById(UUID.fromString(id))
            .orElseThrow { IllegalArgumentException("Conteúdo não encontrado") }
        return content.toResponse()
    }

    fun getContentsByCategory(category: String): List<ContentResponse> {
        return contentRepository.findByCategory(category)
            .map { it.toResponse() }
    }
}

// Extension functions para converter entities em DTOs
fun User.toResponse() = UserResponse(
    id = id.toString(),
    name = name,
    email = email,
    phone = phone,
    profileImageUrl = profileImageUrl,
    points = points,
    level = level,
    totalRecycled = totalRecycled,
    co2Saved = co2Saved,
    visitedPoints = visitedPoints,
    createdAt = createdAt.toString()
)

fun RecyclingPoint.toResponse() = RecyclingPointResponse(
    id = id.toString(),
    name = name,
    address = address,
    latitude = latitude,
    longitude = longitude,
    acceptedMaterials = acceptedMaterials,
    schedule = schedule,
    phone = phone,
    description = description,
    rating = rating,
    isActive = isActive,
    isVerified = isVerified,
    createdBy = createdBy?.toString(),
    createdAt = createdAt.toString()
)

fun Content.toResponse() = ContentResponse(
    id = id.toString(),
    title = title,
    summary = summary,
    content = content,
    category = category,
    author = author,
    readTime = readTime,
    imageUrl = imageUrl,
    tags = tags,
    createdAt = createdAt.toString()
)

