package com.yapp.bol.data.datasource.auth.impl

import com.yapp.bol.data.datasource.auth.AuthDataSource
import com.yapp.bol.data.datasource.auth.impl.datastore.AuthManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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
}
