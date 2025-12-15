package com.example.reciclai.shared.storage

interface StorageService {
    fun saveAuthToken(token: String)
    fun getAuthToken(): String?
    fun setLoggedIn(loggedIn: Boolean)
    fun isLoggedIn(): Boolean
    fun clear()
}
