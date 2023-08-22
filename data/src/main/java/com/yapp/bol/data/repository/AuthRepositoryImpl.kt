package com.yapp.bol.data.repository

import com.yapp.bol.data.datasource.auth.AuthDataSource
import com.yapp.bol.data.mapper.AuthMapper.toDomain
import com.yapp.bol.domain.model.LoginItem
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

    override suspend fun login(type: String, token: String): LoginItem? {
        return dataSource.login(type, token).toDomain()
    }
}
