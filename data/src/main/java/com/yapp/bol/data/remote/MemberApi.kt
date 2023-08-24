package com.yapp.bol.data.remote

import com.yapp.bol.data.model.base.ErrorResponse
import com.yapp.bol.data.model.member.GuestAddApiRequest
import com.yapp.bol.data.model.member.JoinGroupApiRequest
import com.yapp.bol.data.model.member.MemberListResponse
import com.yapp.bol.data.model.member.MemberValidApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MemberApi {

    @GET("/v1/group/{groupId}/member/validateNickname")
    suspend fun getValidateNickName(
        @Path("groupId") groupId: Int,
        @Query("nickname") nickName: String,
    ): Response<MemberValidApiResponse>

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
        @Body guestAddApiRequest: GuestAddApiRequest
    )

    @POST("v1/group/{groupId}/host")
    suspend fun joinGroup(
        @Path("groupId") groupId: String,
        @Body request: JoinGroupApiRequest,
    ): Response<ErrorResponse>
}
