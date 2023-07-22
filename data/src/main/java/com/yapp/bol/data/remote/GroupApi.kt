package com.yapp.bol.data.remote

import com.yapp.bol.data.model.group.GameApiResponse
import com.yapp.bol.data.model.group.GroupDetailResponse
import com.yapp.bol.data.model.group.MemberValidApiResponse
import com.yapp.bol.data.model.group.NewGroupApiRequest
import com.yapp.bol.data.model.group.NewGroupApiResponse
import com.yapp.bol.data.model.group.GroupSearchApiResponse
import com.yapp.bol.data.model.group.JoinedGroupResponse
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

    @GET("/v1/group/{groupId}/game")
    suspend fun getGameList(
        @Path("groupId") groupId: Int,
    ): Response<GameApiResponse>

    @GET("/v1/group/1/member/validateNickname")
    suspend fun getValidateNickName(
        @Query("groupId") groupId: Int,
        @Query("nickname") nickName: String,
    ): Response<MemberValidApiResponse>

    @GET("/v1/group")
    suspend fun getGroupSearchResult(
        @Query("keyword") name: String,
        @Query("pageNumber") page: String,
        @Query("pageSize") pageSize: String,
    ): Response<GroupSearchApiResponse>

    @GET("/v1/user/me/group")
    suspend fun getJoinedGroup(): Response<JoinedGroupResponse>

    @GET("/v1/group/{groupId}")
    suspend fun getGroupDetail(
        @Path("groupId") groupId: Long
    ): Response<GroupDetailResponse>
}
