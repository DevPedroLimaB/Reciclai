package com.example.reciclai.shared.network

import com.example.reciclai.shared.model.*
import com.example.reciclai.shared.util.Resource
import kotlinx.coroutines.delay

@Suppress("UNUSED_PARAMETER", "unused")
class ApiService(private val baseUrl: String = "https://api.reciclai.com/") {

    // DADOS MOCK PARA DESENVOLVIMENTO
    private val mockContents = listOf(
        Content(
            id = "1",
            title = "Como Separar o Lixo Corretamente",
            summary = "Aprenda as melhores práticas para separação de resíduos recicláveis.",
            content = "A separação correta do lixo é fundamental para o meio ambiente...",
            category = "Reciclagem",
            tags = listOf("reciclagem", "meio ambiente", "sustentabilidade"),
            createdAt = "2024-01-15",
            readTime = 5,
            author = "Equipe Reciclai"
        ),
        Content(
            id = "2",
            title = "Benefícios da Reciclagem",
            summary = "Descubra como a reciclagem impacta positivamente o planeta.",
            content = "A reciclagem traz diversos benefícios para o meio ambiente...",
            category = "Sustentabilidade",
            tags = listOf("benefícios", "reciclagem", "planeta"),
            createdAt = "2024-01-10",
            readTime = 7,
            author = "Dr. Verde"
        ),
        Content(
            id = "3",
            title = "Pontos de Coleta Próximos",
            summary = "Encontre os pontos de coleta mais próximos da sua localização.",
            content = "Localize facilmente pontos de coleta em sua região...",
            category = "Localização",
            tags = listOf("coleta", "localização", "pontos"),
            createdAt = "2024-01-05",
            readTime = 3,
            author = "Reciclai Maps"
        )
    )

    private val mockUser = User(
        id = "user123",
        name = "João Silva",
        email = "joao@email.com",
        phone = "+55 11 99999-9999",
        points = 150,
        level = 2,
        totalRecycled = 25.5,
        co2Saved = 12.3,
        createdAt = "2024-01-01",
        updatedAt = "2024-01-15"
    )

    // Auth endpoints com dados mock
    suspend fun login(request: LoginRequest): Resource<AuthResponse> {
        return try {
            delay(1000) // Simula latência da rede
            Resource.Success(
                AuthResponse(
                    accessToken = "mock_access_token_123",
                    refreshToken = "mock_refresh_token_456",
                    user = mockUser
                )
            )
        } catch (e: Exception) {
            Resource.Error("Erro ao fazer login: ${e.message}")
        }
    }

    suspend fun register(request: RegisterRequest): Resource<AuthResponse> {
        return try {
            delay(1000)
            Resource.Success(
                AuthResponse(
                    accessToken = "mock_access_token_new",
                    refreshToken = "mock_refresh_token_new",
                    user = mockUser.copy(
                        name = request.name,
                        email = request.email,
                        phone = request.phone
                    )
                )
            )
        } catch (e: Exception) {
            Resource.Error("Erro ao registrar: ${e.message}")
        }
    }

    // User endpoints com dados mock
    suspend fun getUserProfile(token: String): Resource<User> {
        return try {
            delay(500)
            Resource.Success(mockUser)
        } catch (e: Exception) {
            Resource.Error("Erro ao carregar perfil: ${e.message}")
        }
    }

    suspend fun getUserStats(token: String): Resource<UserStats> {
        return try {
            delay(500)
            Resource.Success(
                UserStats(
                    totalPoints = 150,
                    totalRecycled = 25.5,
                    co2Saved = 12.3,
                    currentLevel = 2,
                    schedulesCompleted = 8,
                    rewardsRedeemed = 3
                )
            )
        } catch (e: Exception) {
            Resource.Error("Erro ao carregar estatísticas: ${e.message}")
        }
    }

    suspend fun getRecyclingHistory(token: String, page: Int = 0, limit: Int = 20): Resource<List<RecyclingHistory>> {
        return try {
            delay(800)
            Resource.Success(
                listOf(
                    RecyclingHistory(
                        id = "hist1",
                        userId = "user123",
                        collectPointId = "point1",
                        collectPointName = "EcoPonto Centro",
                        materials = listOf("Papel", "Plástico"),
                        weight = 2.5,
                        pointsEarned = 25,
                        co2Saved = 1.2,
                        date = "2024-01-10",
                        status = "completed"
                    )
                )
            )
        } catch (e: Exception) {
            Resource.Error("Erro ao carregar histórico: ${e.message}")
        }
    }

    // Content endpoints com dados mock
    suspend fun getContent(category: String? = null, page: Int = 0, limit: Int = 20): Resource<List<Content>> {
        return try {
            delay(1200) // Simula carregamento
            val filteredContent = if (category != null) {
                mockContents.filter { it.category.equals(category, ignoreCase = true) }
            } else {
                mockContents
            }
            Resource.Success(filteredContent)
        } catch (e: Exception) {
            Resource.Error("Erro ao carregar conteúdo: ${e.message}")
        }
    }

    // Collect Points endpoints com dados mock
    suspend fun getCollectPoints(latitude: Double? = null, longitude: Double? = null): Resource<List<CollectPoint>> {
        return try {
            delay(1000)
            Resource.Success(
                listOf(
                    CollectPoint(
                        id = "point1",
                        name = "EcoPonto Centro",
                        address = "Rua das Flores, 123 - Centro",
                        latitude = -23.5505,
                        longitude = -46.6333,
                        acceptedMaterials = listOf("Papel", "Plástico", "Vidro", "Metal"),
                        operatingHours = "08:00 - 18:00",
                        phone = "+55 11 1234-5678",
                        rating = 4.5f,
                        isActive = true
                    ),
                    CollectPoint(
                        id = "point2",
                        name = "Reciclai Vila Madalena",
                        address = "Av. Paulista, 456 - Vila Madalena",
                        latitude = -23.5615,
                        longitude = -46.6565,
                        acceptedMaterials = listOf("Eletrônicos", "Baterias"),
                        operatingHours = "09:00 - 17:00",
                        phone = "+55 11 9876-5432",
                        rating = 4.8f,
                        isActive = true
                    )
                )
            )
        } catch (e: Exception) {
            Resource.Error("Erro ao carregar pontos de coleta: ${e.message}")
        }
    }

    // Rewards endpoints com dados mock
    suspend fun getRewards(token: String): Resource<List<Reward>> {
        return try {
            delay(800)
            Resource.Success(
                listOf(
                    Reward(
                        id = "reward1",
                        title = "Desconto 20% EcoLoja",
                        description = "Ganhe 20% de desconto em produtos sustentáveis",
                        pointsCost = 100,
                        category = "Desconto",
                        isAvailable = true,
                        expiresAt = "2024-12-31"
                    ),
                    Reward(
                        id = "reward2",
                        title = "Muda de Árvore Grátis",
                        description = "Receba uma muda para plantar em casa",
                        pointsCost = 50,
                        category = "Brinde",
                        isAvailable = true
                    )
                )
            )
        } catch (e: Exception) {
            Resource.Error("Erro ao carregar recompensas: ${e.message}")
        }
    }
}
