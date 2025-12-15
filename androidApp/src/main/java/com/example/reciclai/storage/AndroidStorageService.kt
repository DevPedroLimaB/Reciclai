package com.example.reciclai.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.reciclai.shared.storage.StorageService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "reciclai_prefs")

class AndroidStorageService(private val context: Context) : StorageService {

    companion object {
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
        private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    }

    override fun saveAuthToken(token: String) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[AUTH_TOKEN_KEY] = token
            }
        }
    }

    override fun getAuthToken(): String? {
        return runBlocking {
            context.dataStore.data.map { preferences ->
                preferences[AUTH_TOKEN_KEY]
            }.first()
        }
    }

    override fun setLoggedIn(loggedIn: Boolean) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[IS_LOGGED_IN_KEY] = loggedIn
            }
        }
    }

    override fun isLoggedIn(): Boolean {
        return runBlocking {
            context.dataStore.data.map { preferences ->
                preferences[IS_LOGGED_IN_KEY] ?: false
            }.first()
        }
    }

    override fun clear() {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences.clear()
            }
        }
    }
}

