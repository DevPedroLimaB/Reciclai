package com.example.reciclai.shared.storage

class JvmStorageService : StorageService {
    private val storage = mutableMapOf<String, String>()

    override fun saveAuthToken(token: String) {
        storage["auth_token"] = token
    }

    override fun getAuthToken(): String? {
        return storage["auth_token"]
    }

    override fun setLoggedIn(loggedIn: Boolean) {
        storage["is_logged_in"] = loggedIn.toString()
    }

    override fun isLoggedIn(): Boolean {
        return storage["is_logged_in"]?.toBoolean() ?: false
    }

    override fun clear() {
        storage.clear()
    }
}
