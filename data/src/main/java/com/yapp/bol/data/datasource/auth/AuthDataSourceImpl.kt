package com.yapp.bol.data.datasource.auth

import com.yapp.bol.data.datasource.auth.LoginType.toDomain
import com.yapp.bol.data.model.auth.LoginRequest
import com.yapp.bol.data.model.auth.LoginResponse
import com.yapp.bol.data.remote.AuthApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Provider

class AuthDataSourceImpl @Inject constructor(
    private val authManager: AuthManager,
    private val authApiProvider: Provider<AuthApi>,
) : AuthDataSource {

    private val authApi: AuthApi
        get() = authApiProvider.get()

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

    override suspend fun login(type: String, token: String): LoginResponse? {
        return authApi.postOAuthApi(LoginRequest(type.toDomain(), token)).body()
    }
}
