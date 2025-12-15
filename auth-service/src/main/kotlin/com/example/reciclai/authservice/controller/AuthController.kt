package com.example.reciclai.authservice.controller

import com.example.reciclai.authservice.dto.*
import com.example.reciclai.authservice.service.AuthService
import org.springframework.http.ResponseEntity
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

    @GetMapping("/me/{userId}")
    fun getCurrentUser(@PathVariable userId: String): ResponseEntity<ApiResponse<UserResponse>> {
        return try {
            val user = authService.getCurrentUser(userId)
            ResponseEntity.ok(ApiResponse(true, data = user))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(ApiResponse(false, e.message, null))
        }
    }

    @GetMapping("/health")
    fun health(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(mapOf("status" to "UP", "service" to "auth-service"))
    }
}

