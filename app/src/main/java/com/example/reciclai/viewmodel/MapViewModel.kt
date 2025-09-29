package com.example.reciclai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclai.model.CollectPoint
import com.example.reciclai.model.Schedule
import com.example.reciclai.network.CreateScheduleRequest
import com.example.reciclai.network.UpdateScheduleRequest
import com.example.reciclai.repository.CollectPointRepository
import com.example.reciclai.repository.ScheduleRepository
import com.example.reciclai.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val collectPointRepository: CollectPointRepository,
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    private val _collectPoints = MutableStateFlow<Resource<List<CollectPoint>>?>(null)
    val collectPoints: StateFlow<Resource<List<CollectPoint>>?> = _collectPoints.asStateFlow()

    private val _selectedCollectPoint = MutableStateFlow<CollectPoint?>(null)
    val selectedCollectPoint: StateFlow<CollectPoint?> = _selectedCollectPoint.asStateFlow()

    private val _scheduleCreationState = MutableStateFlow<Resource<Schedule>?>(null)
    val scheduleCreationState: StateFlow<Resource<Schedule>?> = _scheduleCreationState.asStateFlow()

    fun getCollectPoints(latitude: Double? = null, longitude: Double? = null, radius: Double? = null) {
        viewModelScope.launch {
            _collectPoints.value = Resource.Loading()
            _collectPoints.value = collectPointRepository.getCollectPoints(latitude, longitude, radius)
        }
    }

    fun getCollectPoint(id: String) {
        viewModelScope.launch {
            val result = collectPointRepository.getCollectPoint(id)
            if (result is Resource.Success) {
                _selectedCollectPoint.value = result.data
            }
        }
    }

    fun selectCollectPoint(collectPoint: CollectPoint) {
        _selectedCollectPoint.value = collectPoint
    }

    fun createSchedule(
        collectPointId: String,
        scheduledDate: String,
        timeSlot: String,
        materials: List<String>,
        estimatedWeight: Double,
        notes: String? = null
    ) {
        viewModelScope.launch {
            _scheduleCreationState.value = Resource.Loading()
            val request = CreateScheduleRequest(
                collectPointId = collectPointId,
                scheduledDate = scheduledDate,
                timeSlot = timeSlot,
                materials = materials,
                estimatedWeight = estimatedWeight,
                notes = notes
            )
            _scheduleCreationState.value = scheduleRepository.createSchedule(request)
        }
    }

    fun clearScheduleCreationState() {
        _scheduleCreationState.value = null
    }
}