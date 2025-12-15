package com.example.reciclai.shared.storage

import kotlinx.browser.localStorage

class JsStorageService : StorageService {

    override fun saveAuthToken(token: String) {
        localStorage.setItem("auth_token", token)
    }

    override fun getAuthToken(): String? {
        return localStorage.getItem("auth_token")
    }

    override fun setLoggedIn(loggedIn: Boolean) {
        localStorage.setItem("is_logged_in", loggedIn.toString())
    }

    override fun isLoggedIn(): Boolean {
        return localStorage.getItem("is_logged_in")?.toBoolean() ?: false
    }

    override fun clear() {
        localStorage.removeItem("auth_token")
        localStorage.removeItem("is_logged_in")
    }
}
