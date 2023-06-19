package com.yapp.bol.data.remote

import com.yapp.bol.data.model.base.BaseResponse
import com.yapp.bol.data.model.group.JoinGroupApiRequest
import com.yapp.bol.data.model.group.NewGroupApiRequest
import com.yapp.bol.data.model.group.NewGroupApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface GroupApi {

    @POST("v1/group")
    suspend fun postOAuthApi(
        @Body newGroupApiRequest: NewGroupApiRequest,
    ): Response<NewGroupApiResponse>

    @POST("v1/group/{groupId}/member/join")
    suspend fun joinGroup(
        @Path("groupId") groupId: String,
        @Body request: JoinGroupApiRequest,
    ): Response<BaseResponse>
}
