package com.yapp.bol.app.di

import com.yapp.bol.app.BuildConfig
import com.yapp.bol.domain.usecase.auth.GetAccessTokenUseCase
import com.yapp.bol.domain.usecase.auth.GetRefreshTokenUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.closeQuietly
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = chain.request().newBuilder()
        var token = runBlocking { getAccessTokenUseCase().first() }

        builder
            .addHeader(AUTHORIZATION_HEADER, "Bearer $token")
            .addHeader(USER_AGENT_HEADER, "Android/${BuildConfig.VERSION_NAME}")

        var response = chain.proceed(builder.build())

        if (response.isUnauthorized()) {
            response.closeQuietly()

            token = runBlocking { getRefreshTokenUseCase().first() }

            response = chain.proceed(
                request.newBuilder()
                    .header(AUTHORIZATION_HEADER, "Bearer $token")
                    .build(),
            )
        }
        return response
    }

    private fun Response.isUnauthorized(): Boolean = code == HttpURLConnection.HTTP_FORBIDDEN
}

private const val AUTHORIZATION_HEADER = "authorization"
private const val USER_AGENT_HEADER = "User-Agent"
