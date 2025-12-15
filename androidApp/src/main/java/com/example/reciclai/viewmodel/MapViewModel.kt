package com.example.reciclai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclai.shared.model.RecyclingPoint
import com.example.reciclai.shared.repository.RecyclingRepository
import com.example.reciclai.shared.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val recyclingRepository: RecyclingRepository
) : ViewModel() {

    private val _recyclingPointsState = MutableStateFlow<Resource<List<RecyclingPoint>>>(Resource.Loading())
    val recyclingPointsState: StateFlow<Resource<List<RecyclingPoint>>> = _recyclingPointsState.asStateFlow()

    private val _addPointState = MutableStateFlow<Resource<Boolean>>(Resource.Success(false))
    val addPointState: StateFlow<Resource<Boolean>> = _addPointState.asStateFlow()

    private val _filteredMaterial = MutableStateFlow<String?>(null)
    val filteredMaterial: StateFlow<String?> = _filteredMaterial.asStateFlow()

    private var allRecyclingPoints: List<RecyclingPoint> = emptyList()

    init {
        loadRecyclingPoints()
    }

    fun loadRecyclingPoints() {
        viewModelScope.launch {
            try {
                _recyclingPointsState.value = Resource.Loading()

                // Simula dados de exemplo para demonstração
                val mockPoints = createMockRecyclingPoints()
                allRecyclingPoints = mockPoints

                // Aplica filtro se houver
                val filteredPoints = if (_filteredMaterial.value != null) {
                    mockPoints.filter { point ->
                        point.acceptedMaterials.contains(_filteredMaterial.value)
                    }
                } else {
                    mockPoints
                }

                _recyclingPointsState.value = Resource.Success(filteredPoints)
            } catch (e: Exception) {
                _recyclingPointsState.value = Resource.Error("Erro ao carregar pontos de reciclagem: ${e.message}")
            }
        }
    }

    fun filterByMaterial(material: String?) {
        _filteredMaterial.value = material

        val filteredPoints = if (material != null) {
            allRecyclingPoints.filter { point ->
                point.acceptedMaterials.contains(material)
            }
        } else {
            allRecyclingPoints
        }

        _recyclingPointsState.value = Resource.Success(filteredPoints)
    }

    fun addRecyclingPoint(
        name: String,
        address: String,
        phone: String?,
        schedule: String,
        description: String?,
        acceptedMaterials: List<String>
    ) {
        viewModelScope.launch {
            try {
                _addPointState.value = Resource.Loading()

                val newPoint = RecyclingPoint(
                    id = "point_${System.currentTimeMillis()}",
                    name = name,
                    address = address,
                    phone = phone,
                    schedule = schedule,
                    description = description,
                    acceptedMaterials = acceptedMaterials,
                    latitude = -23.550520, // São Paulo como exemplo
                    longitude = -46.633308,
                    isVerified = false
                )

                // Simula adição do ponto
                allRecyclingPoints = allRecyclingPoints + newPoint

                // Atualiza a lista exibida
                filterByMaterial(_filteredMaterial.value)

                _addPointState.value = Resource.Success(true)
            } catch (e: Exception) {
                _addPointState.value = Resource.Error("Erro ao adicionar ponto: ${e.message}")
            }
        }
    }

    fun refresh() {
        loadRecyclingPoints()
    }

    private fun createMockRecyclingPoints(): List<RecyclingPoint> {
        return listOf(
            RecyclingPoint(
                id = "1",
                name = "Ecoponto Vila Madalena",
                address = "Rua Harmonia, 123 - Vila Madalena, São Paulo - SP",
                phone = "(11) 3456-7890",
                schedule = "Segunda a Sexta: 8h às 17h",
                description = "Ponto de coleta municipal com ampla variedade de materiais aceitos.",
                acceptedMaterials = listOf("Papel", "Plástico", "Vidro", "Metal", "Eletrônicos"),
                latitude = -23.550520,
                longitude = -46.633308,
                isVerified = true
            ),
            RecyclingPoint(
                id = "2",
                name = "Cooperativa ReciclaVida",
                address = "Av. Paulista, 456 - Bela Vista, São Paulo - SP",
                phone = "(11) 2345-6789",
                schedule = "Segunda a Sábado: 7h às 16h",
                description = "Cooperativa especializada em reciclagem de papel e papelão.",
                acceptedMaterials = listOf("Papel", "Papelão"),
                latitude = -23.561684,
                longitude = -46.656139,
                isVerified = true
            ),
            RecyclingPoint(
                id = "3",
                name = "Centro de Reciclagem Ibirapuera",
                address = "Parque Ibirapuera - Portão 2, São Paulo - SP",
                phone = null,
                schedule = "Todos os dias: 6h às 22h",
                description = "Ponto de coleta no parque, focado em materiais básicos.",
                acceptedMaterials = listOf("Plástico", "Vidro", "Metal"),
                latitude = -23.587416,
                longitude = -46.657634,
                isVerified = true
            ),
            RecyclingPoint(
                id = "4",
                name = "EcoTech Eletrônicos",
                address = "Rua Augusta, 789 - Consolação, São Paulo - SP",
                phone = "(11) 3789-0123",
                schedule = "Segunda a Sexta: 9h às 18h",
                description = "Especializada em descarte responsável de eletrônicos e pilhas.",
                acceptedMaterials = listOf("Eletrônicos", "Pilhas"),
                latitude = -23.554050,
                longitude = -46.662050,
                isVerified = true
            ),
            RecyclingPoint(
                id = "5",
                name = "Reciclagem Sustentável Ltda",
                address = "Rua Oscar Freire, 321 - Jardins, São Paulo - SP",
                phone = "(11) 4567-8901",
                schedule = "Segunda a Sexta: 8h às 17h, Sábado: 8h às 12h",
                description = "Aceita diversos tipos de materiais, incluindo óleo de cozinha usado.",
                acceptedMaterials = listOf("Papel", "Plástico", "Vidro", "Metal", "Óleo de Cozinha"),
                latitude = -23.562108,
                longitude = -46.669691,
                isVerified = true
            ),
            RecyclingPoint(
                id = "6",
                name = "Ponto Verde Shopping",
                address = "Shopping Center Norte - Piso L1, São Paulo - SP",
                phone = "(11) 5678-9012",
                schedule = "Segunda a Sábado: 10h às 22h, Domingo: 12h às 20h",
                description = "Ponto de coleta em shopping center, focado na conveniência do público.",
                acceptedMaterials = listOf("Papel", "Plástico", "Pilhas"),
                latitude = -23.519080,
                longitude = -46.617510,
                isVerified = false
            )
        )
    }
}
