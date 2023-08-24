package com.yapp.bol.data.remote

import com.yapp.bol.data.model.auth.LoginRequest
import com.yapp.bol.data.model.auth.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("v1/auth/login")
    suspend fun postOAuthApi(
        @Body oAuthApiRequest: LoginRequest,
    ): Response<LoginResponse>
}
