package com.example.reciclai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclai.model.User
import com.example.reciclai.model.RecyclingHistory
import com.example.reciclai.network.UserStats
import com.example.reciclai.repository.UserRepository
import com.example.reciclai.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userProfile = MutableStateFlow<Resource<User>?>(null)
    val userProfile: StateFlow<Resource<User>?> = _userProfile.asStateFlow()

    private val _recyclingHistory = MutableStateFlow<Resource<List<RecyclingHistory>>?>(null)
    val recyclingHistory: StateFlow<Resource<List<RecyclingHistory>>?> = _recyclingHistory.asStateFlow()

    private val _userStats = MutableStateFlow<Resource<UserStats>?>(null)
    val userStats: StateFlow<Resource<UserStats>?> = _userStats.asStateFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            _userProfile.value = Resource.Loading()
            _userProfile.value = userRepository.getUserProfile()
        }
    }

    fun updateProfile(user: User) {
        viewModelScope.launch {
            _userProfile.value = Resource.Loading()
            _userProfile.value = userRepository.updateProfile(user)
        }
    }

    fun getRecyclingHistory(page: Int = 1, limit: Int = 20) {
        viewModelScope.launch {
            _recyclingHistory.value = Resource.Loading()
            _recyclingHistory.value = userRepository.getRecyclingHistory(page, limit)
        }
    }

    fun getUserStats() {
        viewModelScope.launch {
            _userStats.value = Resource.Loading()
            _userStats.value = userRepository.getUserStats()
        }
    }
}