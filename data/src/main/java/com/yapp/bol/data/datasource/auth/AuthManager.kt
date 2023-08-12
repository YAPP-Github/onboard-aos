package com.yapp.bol.data.datasource.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthManager @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    val accessKey: Flow<String> = dataStore.data.map { preferences ->
        preferences[USER_ACCESS_PREF_KEY].orEmpty()
    }

    val refreshKey: Flow<String> = dataStore.data.map { preferences ->
        preferences[USER_REFRESH_PREF_KEY].orEmpty()
    }

    suspend fun saveAccessToken(token: String) {
        dataStore.edit {
            it[USER_ACCESS_PREF_KEY] = token
        }
    }

    suspend fun saveRefreshToken(token: String) {
        dataStore.edit {
            it[USER_REFRESH_PREF_KEY] = token
        }
    }

    suspend fun deleteAccessToken() {
        dataStore.edit {
            it.remove(USER_ACCESS_PREF_KEY)
        }
    }

    suspend fun deleteRefreshToken() {
        dataStore.edit {
            it.remove(USER_REFRESH_PREF_KEY)
        }
    }

    companion object {
        private const val USER_ACCESS_KEY = "accessKey"
        private const val USER_REFRESH_KEY = "refreshKey"
        val USER_ACCESS_PREF_KEY = stringPreferencesKey(USER_ACCESS_KEY)
        val USER_REFRESH_PREF_KEY = stringPreferencesKey(USER_REFRESH_KEY)
    }
}
