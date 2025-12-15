package com.example.reciclai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclai.repository.LocalAuthRepository
import com.example.reciclai.shared.model.User
import com.example.reciclai.shared.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel de autenticação com banco de dados local
 * Gerencia login obrigatório e persistência de sessão
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val localAuthRepository: LocalAuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<Resource<User?>>(Resource.Success(null))
    val authState: StateFlow<Resource<User?>> = _authState.asStateFlow()

    private val _userState = MutableStateFlow<Resource<User?>>(Resource.Success(null))
    val userState: StateFlow<Resource<User?>> = _userState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    init {
        checkAuthStatus()
    }

    /**
     * Verifica status de autenticação no banco local
     */
    private fun checkAuthStatus() {
        viewModelScope.launch {
            try {
                val isLoggedIn = localAuthRepository.isLoggedIn()
                _isLoggedIn.value = isLoggedIn

                if (isLoggedIn) {
                    val user = localAuthRepository.getLoggedInUser()
                    _userState.value = Resource.Success(user)
                    _authState.value = Resource.Success(user)
                } else {
                    _userState.value = Resource.Success(null)
                    _authState.value = Resource.Success(null)
                }
            } catch (e: Exception) {
                _isLoggedIn.value = false
                _userState.value = Resource.Error("Erro ao verificar login: ${e.message}")
            }
        }
    }

    /**
     * Faz login com credenciais
     * Dados são salvos no banco de dados local
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _authState.value = Resource.Loading()

                // Valida campos
                if (email.isBlank() || password.isBlank()) {
                    _authState.value = Resource.Error("Preencha todos os campos")
                    return@launch
                }

                // Faz login no banco local
                val result = localAuthRepository.login(email, password)

                when (result) {
                    is Resource.Success -> {
                        _authState.value = Resource.Success(result.data?.user)
                        _userState.value = Resource.Success(result.data?.user)
                        _isLoggedIn.value = true
                    }
                    is Resource.Error -> {
                        _authState.value = Resource.Error(result.message ?: "Erro no login")
                        _isLoggedIn.value = false
                    }
                    is Resource.Loading -> {
                        _authState.value = Resource.Loading()
                    }
                }
            } catch (e: Exception) {
                _authState.value = Resource.Error("Erro inesperado: ${e.message}")
                _isLoggedIn.value = false
            }
        }
    }

    /**
     * Registra novo usuário
     * Dados são salvos no banco de dados local
     */
    fun register(name: String, email: String, password: String, phone: String? = null) {
        viewModelScope.launch {
            try {
                _authState.value = Resource.Loading()

                // Valida campos
                if (name.isBlank() || email.isBlank() || password.isBlank()) {
                    _authState.value = Resource.Error("Preencha todos os campos obrigatórios")
                    return@launch
                }

                if (password.length < 6) {
                    _authState.value = Resource.Error("A senha deve ter no mínimo 6 caracteres")
                    return@launch
                }

                // Registra no banco local
                val result = localAuthRepository.register(name, email, password, phone)

                when (result) {
                    is Resource.Success -> {
                        _authState.value = Resource.Success(result.data?.user)
                        _userState.value = Resource.Success(result.data?.user)
                        _isLoggedIn.value = true
                    }
                    is Resource.Error -> {
                        _authState.value = Resource.Error(result.message ?: "Erro no registro")
                        _isLoggedIn.value = false
                    }
                    is Resource.Loading -> {
                        _authState.value = Resource.Loading()
                    }
                }
            } catch (e: Exception) {
                _authState.value = Resource.Error("Erro inesperado: ${e.message}")
                _isLoggedIn.value = false
            }
        }
    }

    /**
     * Faz logout e remove sessão do banco de dados
     */
    fun logout() {
        viewModelScope.launch {
            try {
                localAuthRepository.logout()
                _authState.value = Resource.Success(null)
                _userState.value = Resource.Success(null)
                _isLoggedIn.value = false
            } catch (e: Exception) {
                // Força logout mesmo com erro
                _authState.value = Resource.Success(null)
                _userState.value = Resource.Success(null)
                _isLoggedIn.value = false
            }
        }
    }

    /**
     * Verifica se usuário está logado
     */
    fun isLoggedIn(): Boolean {
        return _isLoggedIn.value
    }

    /**
     * Limpa estado de autenticação (após navegação)
     */
    fun clearAuthState() {
        _authState.value = Resource.Success(_userState.value.data)
    }
}
