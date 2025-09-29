package com.example.reciclai.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(context: Context) {
    
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    
    fun getAuthToken(): String? {
        return sharedPreferences.getString(Constants.AUTH_TOKEN_KEY, null)
    }
    
    fun setAuthToken(token: String) {
        sharedPreferences.edit()
            .putString(Constants.AUTH_TOKEN_KEY, token)
            .apply()
    }
    
    fun getRefreshToken(): String? {
        return sharedPreferences.getString(Constants.REFRESH_TOKEN_KEY, null)
    }
    
    fun setRefreshToken(token: String) {
        sharedPreferences.edit()
            .putString(Constants.REFRESH_TOKEN_KEY, token)
            .apply()
    }
    
    fun getUserId(): String? {
        return sharedPreferences.getString(Constants.USER_ID_KEY, null)
    }
    
    fun setUserId(userId: String) {
        sharedPreferences.edit()
            .putString(Constants.USER_ID_KEY, userId)
            .apply()
    }
    
    fun getFcmToken(): String? {
        return sharedPreferences.getString(Constants.FCM_TOKEN_KEY, null)
    }
    
    fun setFcmToken(token: String) {
        sharedPreferences.edit()
            .putString(Constants.FCM_TOKEN_KEY, token)
            .apply()
    }
    
    fun isLoggedIn(): Boolean {
        return getAuthToken() != null && getUserId() != null
    }
    
    fun clearUserData() {
        sharedPreferences.edit()
            .remove(Constants.AUTH_TOKEN_KEY)
            .remove(Constants.REFRESH_TOKEN_KEY)
            .remove(Constants.USER_ID_KEY)
            .apply()
    }
}