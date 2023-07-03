package com.yapp.bol.data.remote

import com.yapp.bol.data.model.login.LoginRequest
import com.yapp.bol.data.model.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("v1/auth/login")
    suspend fun postOAuthApi(
        @Body oAuthApiRequest: LoginRequest
    ): Response<LoginResponse>
}
