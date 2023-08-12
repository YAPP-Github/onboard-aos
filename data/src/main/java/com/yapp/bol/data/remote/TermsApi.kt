package com.yapp.bol.data.remote

import com.yapp.bol.data.model.login.TermsRequest
import com.yapp.bol.data.model.login.TermsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TermsApi {

    @GET("/v1/terms")
    suspend fun getTerms(): Response<TermsResponse>

    @POST("/v1/terms")
    suspend fun postTerms(
        @Body termsRequest: TermsRequest
    )
}
