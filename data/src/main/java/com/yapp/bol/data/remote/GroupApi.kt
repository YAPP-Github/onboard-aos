package com.yapp.bol.data.remote

import com.yapp.bol.data.model.group.GameApiResponse
import com.yapp.bol.data.model.group.NewGroupApiRequest
import com.yapp.bol.data.model.group.NewGroupApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupApi {

    @POST("v1/group")
    suspend fun postOAuthApi(
        @Body newGroupApiRequest: NewGroupApiRequest
    ): Response<NewGroupApiResponse>

    @GET("v1/game/{groupId}")
    suspend fun getGameList(
        @Path("groupId") groupId: String,
    ): Response<GameApiResponse>
}
