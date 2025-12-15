import kotlinx.browser.window
import kotlinx.coroutines.*
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Headers
import kotlin.js.Promise
import kotlin.js.json

// Configuração da API - MICROSERVIÇOS
object ApiConfig {
    // Auth Service - Porta 8082 (CORRETO!)
    val AUTH_SERVICE_URL = when {
        window.location.hostname == "localhost" -> "http://localhost:8082/api"
        else -> "/auth-api"
    }

    // Backend Service - Porta 8080
    val BACKEND_SERVICE_URL = when {
        window.location.hostname == "localhost" -> "http://localhost:8080/api"
        else -> "/api"
    }
}

// Serviço de API
object ApiService {
    private var authToken: String? = null

    fun setAuthToken(token: String) {
        authToken = token
    }

    fun clearAuthToken() {
        authToken = null
    }

    private fun getHeaders(): Headers {
        val headers = Headers()
        headers.append("Content-Type", "application/json")
        authToken?.let { headers.append("Authorization", "Bearer $it") }
        return headers
    }

    // GET request - usa baseUrl específico
    suspend fun get(endpoint: String, baseUrl: String = ApiConfig.BACKEND_SERVICE_URL): String {
        return try {
            val response = window.fetch(
                "$baseUrl$endpoint",
                RequestInit(
                    method = "GET",
                    headers = getHeaders()
                )
            ).await()

            if (!response.ok) {
                throw Exception("Erro HTTP: ${response.status}")
            }

            response.text().await()
        } catch (e: Exception) {
            console.error("Erro na requisição GET $endpoint:", e)
            throw e
        }
    }

    // POST request - usa baseUrl específico
    suspend fun post(endpoint: String, body: Any, baseUrl: String = ApiConfig.BACKEND_SERVICE_URL): String {
        return try {
            val response = window.fetch(
                "$baseUrl$endpoint",
                RequestInit(
                    method = "POST",
                    headers = getHeaders(),
                    body = JSON.stringify(body)
                )
            ).await()

            if (!response.ok) {
                throw Exception("Erro HTTP: ${response.status}")
            }

            response.text().await()
        } catch (e: Exception) {
            console.error("Erro na requisição POST $endpoint:", e)
            throw e
        }
    }

    // PUT request
    suspend fun put(endpoint: String, body: Any, baseUrl: String = ApiConfig.BACKEND_SERVICE_URL): String {
        return try {
            val response = window.fetch(
                "$baseUrl$endpoint",
                RequestInit(
                    method = "PUT",
                    headers = getHeaders(),
                    body = JSON.stringify(body)
                )
            ).await()

            if (!response.ok) {
                throw Exception("Erro HTTP: ${response.status}")
            }

            response.text().await()
        } catch (e: Exception) {
            console.error("Erro na requisição PUT $endpoint:", e)
            throw e
        }
    }

    // DELETE request
    suspend fun delete(endpoint: String, baseUrl: String = ApiConfig.BACKEND_SERVICE_URL): String {
        return try {
            val response = window.fetch(
                "$baseUrl$endpoint",
                RequestInit(
                    method = "DELETE",
                    headers = getHeaders()
                )
            ).await()

            if (!response.ok) {
                throw Exception("Erro HTTP: ${response.status}")
            }

            response.text().await()
        } catch (e: Exception) {
            console.error("Erro na requisição DELETE $endpoint:", e)
            throw e
        }
    }
}

// Modelos de dados
data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val name: String, val email: String, val password: String, val phone: String? = null)
data class LoginResponse(val token: String, val user: User)
data class User(val id: Long, val name: String, val email: String, val points: Int)
data class RecyclingPoint(
    val id: Long,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val operatingHours: String,
    val acceptedMaterials: List<String>,
    val phone: String?,
    val description: String?
)

// Repositório de autenticação - USA AUTH SERVICE (porta 8081)
object AuthRepository {
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val body = json(
                "email" to email,
                "password" to password
            )
            val response = ApiService.post("/auth/login", body, ApiConfig.AUTH_SERVICE_URL)
            val apiResponse = JSON.parse<dynamic>(response)

            console.log("Resposta do Auth Service:", apiResponse)

            if (apiResponse.success != true) {
                throw Exception(apiResponse.message as? String ?: "Erro no login")
            }

            val data = apiResponse.data
            val loginResponse = LoginResponse(
                token = data.token as String,
                user = User(
                    id = (data.user.id as String).hashCode().toLong(),
                    name = data.user.name as String,
                    email = data.user.email as String,
                    points = (data.user.points as? Number)?.toInt() ?: 0
                )
            )

            ApiService.setAuthToken(loginResponse.token)
            Result.success(loginResponse)
        } catch (e: Exception) {
            console.error("Erro no login:", e)
            Result.failure(e)
        }
    }

    suspend fun register(name: String, email: String, password: String, phone: String? = null): Result<LoginResponse> {
        return try {
            val body = json(
                "name" to name,
                "email" to email,
                "password" to password,
                "phone" to phone
            )
            val response = ApiService.post("/auth/register", body, ApiConfig.AUTH_SERVICE_URL)
            val apiResponse = JSON.parse<dynamic>(response)

            console.log("Resposta do registro:", apiResponse)

            if (apiResponse.success != true) {
                throw Exception(apiResponse.message as? String ?: "Erro no registro")
            }

            val data = apiResponse.data
            val loginResponse = LoginResponse(
                token = data.token as String,
                user = User(
                    id = (data.user.id as String).hashCode().toLong(),
                    name = data.user.name as String,
                    email = data.user.email as String,
                    points = (data.user.points as? Number)?.toInt() ?: 0
                )
            )

            ApiService.setAuthToken(loginResponse.token)
            Result.success(loginResponse)
        } catch (e: Exception) {
            console.error("Erro no registro:", e)
            Result.failure(e)
        }
    }

    fun logout() {
        ApiService.clearAuthToken()
    }
}

// Repositório de pontos de reciclagem - USA BACKEND SERVICE (porta 8080)
object RecyclingPointRepository {
    suspend fun getAllPoints(): Result<List<RecyclingPoint>> {
        return try {
            val response = ApiService.get("/recycling-points/all", ApiConfig.BACKEND_SERVICE_URL)
            val apiResponse = JSON.parse<dynamic>(response)

            console.log("Resposta da API de pontos:", apiResponse)

            // Verificar se a resposta tem o formato ApiResponse
            val dataArray = if (apiResponse.success == true && apiResponse.data != null) {
                apiResponse.data as Array<dynamic>
            } else if (apiResponse is Array<*>) {
                apiResponse as Array<dynamic>
            } else {
                throw Exception("Formato de resposta inválido")
            }

            val points = dataArray.map { item ->
                RecyclingPoint(
                    id = when (val id = item.id) {
                        is String -> id.hashCode().toLong()
                        is Number -> id.toLong()
                        else -> 0L
                    },
                    name = item.name as String,
                    address = item.address as String,
                    latitude = (item.latitude as Number).toDouble(),
                    longitude = (item.longitude as Number).toDouble(),
                    operatingHours = (item.schedule ?: item.operatingHours) as? String ?: "Não informado",
                    acceptedMaterials = (item.acceptedMaterials as? Array<String>)?.toList() ?: emptyList(),
                    phone = item.phone as? String,
                    description = item.description as? String
                )
            }

            console.log("✅ Pontos parseados com sucesso:", points.size)
            Result.success(points)
        } catch (e: Exception) {
            console.error("❌ Erro ao buscar pontos:", e)
            Result.failure(e)
        }
    }

    suspend fun createPoint(point: RecyclingPoint): Result<RecyclingPoint> {
        return try {
            val body = json(
                "name" to point.name,
                "address" to point.address,
                "latitude" to point.latitude,
                "longitude" to point.longitude,
                "operatingHours" to point.operatingHours,
                "acceptedMaterials" to point.acceptedMaterials.toTypedArray(),
                "phone" to point.phone,
                "description" to point.description
            )
            val response = ApiService.post("/recycling-points", body, ApiConfig.BACKEND_SERVICE_URL)
            val apiResponse = JSON.parse<dynamic>(response)

            if (apiResponse.success != true) {
                throw Exception(apiResponse.message as? String ?: "Erro ao criar ponto")
            }

            val item = apiResponse.data
            val createdPoint = RecyclingPoint(
                id = (item.id as Number).toLong(),
                name = item.name as String,
                address = item.address as String,
                latitude = (item.latitude as Number).toDouble(),
                longitude = (item.longitude as Number).toDouble(),
                operatingHours = item.operatingHours as? String ?: "Não informado",
                acceptedMaterials = (item.acceptedMaterials as? Array<String>)?.toList() ?: emptyList(),
                phone = item.phone as? String,
                description = item.description as? String
            )

            Result.success(createdPoint)
        } catch (e: Exception) {
            console.error("Erro ao criar ponto:", e)
            Result.failure(e)
        }
    }
}

// Função auxiliar para lançar coroutines
fun launchAsync(block: suspend CoroutineScope.() -> Unit) {
    GlobalScope.launch {
        try {
            block()
        } catch (e: Exception) {
            console.error("Erro na coroutine:", e)
        }
    }
}
