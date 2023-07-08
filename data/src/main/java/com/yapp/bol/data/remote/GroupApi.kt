package com.yapp.bol.data.remote

import com.yapp.bol.data.model.group.GuestAddApiRequest
import com.yapp.bol.data.model.group.MemberListResponse
import com.yapp.bol.data.model.group.response.ProfileUploadResponse
import com.yapp.bol.data.model.group.response.GameApiResponse
import com.yapp.bol.data.model.group.response.MemberValidApiResponse
import com.yapp.bol.data.model.group.request.NewGroupApiRequest
import com.yapp.bol.data.model.group.response.NewGroupApiResponse
import com.yapp.bol.data.model.group.response.GroupSearchApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupApi {

    @POST("v1/group")
    suspend fun postOAuthApi(
        @Body newGroupApiRequest: NewGroupApiRequest,
    ): Response<NewGroupApiResponse>

    @Multipart
    @POST("v1/file")
    suspend fun postProfileUpload(
        @Part file: MultipartBody.Part,
        @Part("purpose") purpose: RequestBody,
    ): Response<ProfileUploadResponse>

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
        @Query("name") name: String,
        @Query("pageNumber") page: String,
        @Query("pageSize") pageSize: String,
    ): Response<GroupSearchApiResponse>

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
}
