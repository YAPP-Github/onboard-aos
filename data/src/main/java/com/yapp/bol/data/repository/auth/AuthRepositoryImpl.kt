package com.yapp.bol.data.repository.auth

import com.yapp.bol.data.datasource.auth.AuthDataSource
import com.yapp.bol.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource,
) : AuthRepository {

    override val accessToken: Flow<String> = dataSource.accessToken

    override val refreshToken: Flow<String> = dataSource.refreshToken

    override suspend fun saveAccessToken(token: String) {
        dataSource.saveAccessToken(token)
    }

    override suspend fun saveRefreshToken(token: String) {
        dataSource.saveRefreshToken(token)
    }

    override suspend fun deleteAccessToken() {
        dataSource.deleteAccessToken()
    }

    override suspend fun deleteRefreshToken() {
        dataSource.deleteRefreshToken()
    }
}
