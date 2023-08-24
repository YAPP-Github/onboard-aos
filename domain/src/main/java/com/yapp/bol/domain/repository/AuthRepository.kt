package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.LoginItem
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val accessToken: Flow<String>

    val refreshToken: Flow<String>

    suspend fun saveAccessToken(token: String)

    suspend fun saveRefreshToken(token: String)

    suspend fun deleteAccessToken()

    suspend fun deleteRefreshToken()

    suspend fun login(type: String, token: String): LoginItem?
}
