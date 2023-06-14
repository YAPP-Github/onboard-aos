package com.yapp.bol.data.datasource.auth

import kotlinx.coroutines.flow.Flow

interface AuthDataSource {

    val accessToken: Flow<String>

    val refreshToken: Flow<String>

    suspend fun saveAccessToken(token: String)

    suspend fun saveRefreshToken(token: String)
}
