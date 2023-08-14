package com.yapp.bol.data.remote

import com.yapp.bol.data.model.group.CheckGroupJonByAccessCodeRequest
import com.yapp.bol.data.model.group.NewGroupApiRequest
import com.yapp.bol.data.model.group.CheckGroupJoinByAccessCodeResponse
import com.yapp.bol.data.model.group.GroupDetailResponse
import com.yapp.bol.data.model.group.GroupSearchApiResponse
import com.yapp.bol.data.model.group.NewGroupApiResponse
import com.yapp.bol.data.model.group.RandomImageResponse
import com.yapp.bol.data.model.group.UserRankApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupApi {

    @POST("v1/group")
    suspend fun postCreateGroup(
        @Body newGroupApiRequest: NewGroupApiRequest,
    ): Response<NewGroupApiResponse>

    @GET("/v1/group/{groupId}/game/{gameId}")
    suspend fun getUserRank(
        @Path("groupId") groupId: Int,
        @Path("gameId") gameId: Int,
    ): Response<UserRankApiResponse>

    @GET("/v1/group/default-image")
    suspend fun getRandomImage(): Response<RandomImageResponse>

    @GET("/v1/group")
    suspend fun getGroupSearchResult(
        @Query("keyword") name: String,
        @Query("pageNumber") page: String,
        @Query("pageSize") pageSize: String,
    ): Response<GroupSearchApiResponse>

    @GET("/v1/group/{groupId}")
    suspend fun getGroupDetail(
        @Path("groupId") groupId: Long
    ): Response<GroupDetailResponse>

    @POST("v1/group/{groupId}/accessCode")
    suspend fun checkGroupJoinAccessCode(
        @Path("groupId") groupId: String,
        @Body accessCode: CheckGroupJonByAccessCodeRequest,
    ): Response<CheckGroupJoinByAccessCodeResponse>
}
