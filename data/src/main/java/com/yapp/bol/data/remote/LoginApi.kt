package com.yapp.bol.data.remote

import com.yapp.bol.data.model.login.TermsResponse
import com.yapp.bol.data.model.login.LoginRequest
import com.yapp.bol.data.model.login.LoginResponse
import com.yapp.bol.data.model.login.OnBoardResponse
import com.yapp.bol.data.model.login.TermsRequest
import com.yapp.bol.data.model.login.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface LoginApi {
    @POST("v1/auth/login")
    suspend fun postOAuthApi(
        @Body oAuthApiRequest: LoginRequest
    ): Response<LoginResponse>

    @GET("/v1/terms")
    suspend fun getTerms(): Response<TermsResponse>

    @POST("/v1/terms")
    suspend fun postTerms(
        @Body termsRequest: TermsRequest
    )

    @GET("/v1/user/me/onboarding")
    suspend fun getOnboard(): Response<OnBoardResponse>

    @PUT("/v1/user/me")
    suspend fun putUserName(
        @Body userRequest: UserRequest
    )
}
