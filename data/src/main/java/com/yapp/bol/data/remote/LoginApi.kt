package com.yapp.bol.data.remote

import com.yapp.bol.data.model.MockApiRequest
import com.yapp.bol.data.model.MockApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("auth/login")
    suspend fun postMockApi(
        @Body mockApiRequest: MockApiRequest,
    ): Response<MockApiResponse>
}
