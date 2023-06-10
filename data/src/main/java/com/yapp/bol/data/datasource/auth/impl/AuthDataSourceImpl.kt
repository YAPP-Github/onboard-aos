package com.yapp.bol.data.datasource.auth.impl

import com.yapp.bol.data.datasource.auth.AuthDataSource
import com.yapp.bol.data.datasource.auth.impl.datastore.AuthManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataSourceImpl @Inject constructor(
    private val dataStore: AuthManager,
) : AuthDataSource {

    override val accessToken: Flow<String> = dataStore.accessKey

    override val refreshToken: Flow<String> = dataStore.refreshKey

    override suspend fun saveAccessToken(token: String) {
        dataStore.saveAccessToken(token)
    }

    override suspend fun saveRefreshToken(token: String) {
        dataStore.saveRefreshToken(token)
    }
}
