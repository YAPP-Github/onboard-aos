package com.yapp.bol.data.remote

import com.yapp.bol.data.model.group.NewGroupApiRequest
import com.yapp.bol.data.model.group.NewGroupApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GroupApi {

    @POST("v1/group")
    suspend fun postMockApi(
        @Body mockApiRequest: NewGroupApiRequest
    ): Response<NewGroupApiResponse>
}
