package com.example.reciclai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclai.network.AuthData
import com.example.reciclai.repository.AuthRepository
import com.example.reciclai.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<Resource<AuthData>?>(null)
    val authState: StateFlow<Resource<AuthData>?> = _authState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(authRepository.isLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = Resource.Loading()
            val result = authRepository.login(email, password)
            _authState.value = result
            if (result is Resource.Success) {
                _isLoggedIn.value = true
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = Resource.Loading()
            val result = authRepository.register(name, email, password)
            _authState.value = result
            if (result is Resource.Success) {
                _isLoggedIn.value = true
            }
        }
    }

    fun socialLogin(provider: String, token: String, name: String, email: String) {
        viewModelScope.launch {
            _authState.value = Resource.Loading()
            val result = authRepository.socialLogin(provider, token, name, email)
            _authState.value = result
            if (result is Resource.Success) {
                _isLoggedIn.value = true
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _isLoggedIn.value = false
            _authState.value = null
        }
    }

    fun clearAuthState() {
        _authState.value = null
    }
}