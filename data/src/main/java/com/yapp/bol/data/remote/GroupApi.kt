package com.yapp.bol.data.remote

import com.yapp.bol.data.model.base.BaseResponse
import com.yapp.bol.data.model.group.GetGroupGameListResponse
import com.yapp.bol.data.model.group.GuestAddApiRequest
import com.yapp.bol.data.model.group.JoinGroupApiRequest
import com.yapp.bol.data.model.group.MemberListResponse
import com.yapp.bol.data.model.group.request.CheckGroupJonByAccessCodeRequest
import com.yapp.bol.data.model.group.response.GameApiResponse
import com.yapp.bol.data.model.group.response.MemberValidApiResponse
import com.yapp.bol.data.model.group.request.NewGroupApiRequest
import com.yapp.bol.data.model.group.response.CheckGroupJoinByAccessCodeResponse
import com.yapp.bol.data.model.group.response.GroupDetailResponse
import com.yapp.bol.data.model.group.response.NewGroupApiResponse
import com.yapp.bol.data.model.group.response.GroupSearchApiResponse
import com.yapp.bol.data.model.group.response.JoinedGroupResponse
import com.yapp.bol.data.model.group.response.RandomImageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupApi {

    @POST("v1/group")
    suspend fun postOAuthApi(
        @Body newGroupApiRequest: NewGroupApiRequest,
    ): Response<NewGroupApiResponse>

    @GET("/v1/group/{groupId}/game")
    suspend fun getGameList(
        @Path("groupId") groupId: Int,
    ): Response<GameApiResponse>

    @GET("/v1/group/{groupId}/member/validateNickname")
    suspend fun getValidateNickName(
        @Path("groupId") groupId: Int,
        @Query("nickname") nickName: String,
    ): Response<MemberValidApiResponse>

    @GET("/v1/group/default-image")
    suspend fun getRandomImage(): Response<RandomImageResponse>

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
        @Path("groupId") groupId: Long,
    ): Response<GroupDetailResponse>

    @GET("/v1/group/{groupId}/member")
    suspend fun getMemberList(
        @Path("groupId") groupId: Int,
        @Query("size") pageSize: Int,
        @Query("cursor") cursor: String?,
        @Query("nickname") nickname: String?,
    ): Response<MemberListResponse>

    @POST("/v1/group/{groupId}/guest")
    suspend fun postGuestMember(
        @Path("groupId") groupId: Int,
        @Body guestAddApiRequest: GuestAddApiRequest,
    )

    @POST("v1/group/{groupId}/accessCode")
    suspend fun checkGroupJoinAccessCode(
        @Path("groupId") groupId: String,
        @Body accessCode: CheckGroupJonByAccessCodeRequest,
    ): Response<CheckGroupJoinByAccessCodeResponse>

    @POST("v1/group/{groupId}/host")
    suspend fun joinGroup(
        @Path("groupId") groupId: String,
        @Body request: JoinGroupApiRequest,
    ): Response<BaseResponse>

    @GET("/v1/group/{groupId}/game")
    suspend fun getGroupGame(
        @Path("groupId") groupId: Int,
    ): Response<GetGroupGameListResponse>
}
