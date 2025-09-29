package com.example.reciclai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclai.model.Schedule
import com.example.reciclai.network.UpdateScheduleRequest
import com.example.reciclai.repository.ScheduleRepository
import com.example.reciclai.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    private val _schedules = MutableStateFlow<Resource<List<Schedule>>?>(null)
    val schedules: StateFlow<Resource<List<Schedule>>?> = _schedules.asStateFlow()

    private val _updateState = MutableStateFlow<Resource<Schedule>?>(null)
    val updateState: StateFlow<Resource<Schedule>?> = _updateState.asStateFlow()

    private val _cancelState = MutableStateFlow<Resource<Unit>?>(null)
    val cancelState: StateFlow<Resource<Unit>?> = _cancelState.asStateFlow()

    fun getUserSchedules(status: String? = null) {
        viewModelScope.launch {
            _schedules.value = Resource.Loading()
            _schedules.value = scheduleRepository.getUserSchedules(status)
        }
    }

    fun updateSchedule(
        scheduleId: String,
        scheduledDate: String? = null,
        timeSlot: String? = null,
        materials: List<String>? = null,
        estimatedWeight: Double? = null,
        notes: String? = null
    ) {
        viewModelScope.launch {
            _updateState.value = Resource.Loading()
            val request = UpdateScheduleRequest(
                scheduledDate = scheduledDate,
                timeSlot = timeSlot,
                materials = materials,
                estimatedWeight = estimatedWeight,
                notes = notes
            )
            _updateState.value = scheduleRepository.updateSchedule(scheduleId, request)
        }
    }

    fun cancelSchedule(scheduleId: String) {
        viewModelScope.launch {
            _cancelState.value = Resource.Loading()
            _cancelState.value = scheduleRepository.cancelSchedule(scheduleId)
        }
    }

    fun clearUpdateState() {
        _updateState.value = null
    }

    fun clearCancelState() {
        _cancelState.value = null
    }
}