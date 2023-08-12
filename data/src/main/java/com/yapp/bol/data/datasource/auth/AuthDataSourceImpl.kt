package com.yapp.bol.data.datasource.auth

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authManager: AuthManager,
) : AuthDataSource {

    override val accessToken: Flow<String> = authManager.accessKey

    override val refreshToken: Flow<String> = authManager.refreshKey

    override suspend fun saveAccessToken(token: String) {
        authManager.saveAccessToken(token)
    }

    override suspend fun saveRefreshToken(token: String) {
        authManager.saveRefreshToken(token)
    }

    override suspend fun deleteAccessToken() {
        authManager.deleteAccessToken()
    }

    override suspend fun deleteRefreshToken() {
        authManager.deleteRefreshToken()
    }
}
