package com.yapp.bol.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val accessToken: Flow<String>

    val refreshToken: Flow<String>

    suspend fun saveAccessToken(token: String)

    suspend fun saveRefreshToken(token: String)
}
