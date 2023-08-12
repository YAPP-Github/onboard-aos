package com.yapp.bol.data.datasource.auth

import com.yapp.bol.data.model.login.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {

    val accessToken: Flow<String>

    val refreshToken: Flow<String>

    suspend fun saveAccessToken(token: String)

    suspend fun saveRefreshToken(token: String)

    suspend fun deleteAccessToken()

    suspend fun deleteRefreshToken()

    suspend fun login(type: String, token: String): LoginResponse?
}
