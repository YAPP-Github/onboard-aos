package com.yapp.bol.data.remote

import com.yapp.bol.data.model.OAuthApiRequest
import com.yapp.bol.data.model.OAuthApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("v1/auth/login")
    suspend fun postOAuthApi(
        @Body oAuthApiRequest: OAuthApiRequest
    ): Response<OAuthApiResponse>
}
