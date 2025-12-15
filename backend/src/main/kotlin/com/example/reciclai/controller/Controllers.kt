package com.example.reciclai.controller

import com.example.reciclai.dto.*
import com.example.reciclai.service.AuthService
import com.example.reciclai.service.RecyclingPointService
import com.example.reciclai.service.ContentService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<ApiResponse<AuthResponse>> {
        return try {
            val response = authService.login(request)
            ResponseEntity.ok(ApiResponse(true, "Login realizado com sucesso", response))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(ApiResponse(false, e.message, null))
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<ApiResponse<AuthResponse>> {
        return try {
            val response = authService.register(request)
            ResponseEntity.ok(ApiResponse(true, "Cadastro realizado com sucesso", response))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(ApiResponse(false, e.message, null))
        }
    }

    @GetMapping("/me")
    fun getCurrentUser(authentication: Authentication): ResponseEntity<ApiResponse<UserResponse>> {
        return try {
            val userId = authentication.principal as String
            val user = authService.getCurrentUser(userId)
            ResponseEntity.ok(ApiResponse(true, data = user))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(ApiResponse(false, e.message, null))
        }
    }
}

@RestController
@RequestMapping("/api/recycling-points")
class RecyclingPointController(
    private val recyclingPointService: RecyclingPointService
) {

    @GetMapping("/all")
    fun getAllPoints(): ResponseEntity<ApiResponse<List<RecyclingPointResponse>>> {
        return try {
            val points = recyclingPointService.getAllPoints()
            ResponseEntity.ok(ApiResponse(true, data = points))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(ApiResponse(false, e.message, null))
        }
    }

    @GetMapping("/{id}")
    fun getPointById(@PathVariable id: String): ResponseEntity<ApiResponse<RecyclingPointResponse>> {
        return try {
            val point = recyclingPointService.getPointById(id)
            ResponseEntity.ok(ApiResponse(true, data = point))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(ApiResponse(false, e.message, null))
        }
    }

    @PostMapping
    fun createPoint(
        @RequestBody request: RecyclingPointRequest,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<RecyclingPointResponse>> {
        return try {
            val userId = authentication.principal as String
            val point = recyclingPointService.createPoint(request, userId)
            ResponseEntity.ok(ApiResponse(true, "Ponto criado com sucesso", point))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(ApiResponse(false, e.message, null))
        }
    }

    @PutMapping("/{id}")
    fun updatePoint(
        @PathVariable id: String,
        @RequestBody request: RecyclingPointRequest
    ): ResponseEntity<ApiResponse<RecyclingPointResponse>> {
        return try {
            val point = recyclingPointService.updatePoint(id, request)
            ResponseEntity.ok(ApiResponse(true, "Ponto atualizado com sucesso", point))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(ApiResponse(false, e.message, null))
        }
    }

    @DeleteMapping("/{id}")
    fun deletePoint(@PathVariable id: String): ResponseEntity<ApiResponse<Nothing>> {
        return try {
            recyclingPointService.deletePoint(id)
            ResponseEntity.ok(ApiResponse(true, "Ponto removido com sucesso", null))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(ApiResponse(false, e.message, null))
        }
    }
}

@RestController
@RequestMapping("/api/contents")
class ContentController(
    private val contentService: ContentService
) {

    @GetMapping
    fun getAllContents(): ResponseEntity<ApiResponse<List<ContentResponse>>> {
        return try {
            val contents = contentService.getAllContents()
            ResponseEntity.ok(ApiResponse(true, data = contents))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(ApiResponse(false, e.message, null))
        }
    }

    @GetMapping("/{id}")
    fun getContentById(@PathVariable id: String): ResponseEntity<ApiResponse<ContentResponse>> {
        return try {
            val content = contentService.getContentById(id)
            ResponseEntity.ok(ApiResponse(true, data = content))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(ApiResponse(false, e.message, null))
        }
    }

    @GetMapping("/category/{category}")
    fun getContentsByCategory(@PathVariable category: String): ResponseEntity<ApiResponse<List<ContentResponse>>> {
        return try {
            val contents = contentService.getContentsByCategory(category)
            ResponseEntity.ok(ApiResponse(true, data = contents))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(ApiResponse(false, e.message, null))
        }
    }
}

