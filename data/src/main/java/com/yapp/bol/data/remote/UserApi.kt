package com.yapp.bol.data.remote

import com.yapp.bol.data.model.group.response.JoinedGroupResponse
import com.yapp.bol.data.model.auth.OnBoardResponse
import com.yapp.bol.data.model.auth.UserRequest
import com.yapp.bol.data.model.user.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserApi {

    @PUT("/v1/user/me")
    suspend fun putUserName(
        @Body userRequest: UserRequest
    )

    @GET("/v1/user/me/group")
    suspend fun getJoinedGroup(): Response<JoinedGroupResponse>

    @GET("/v1/user/me/onboarding")
    suspend fun getOnboard(): Response<OnBoardResponse>

    @GET("/v1/user/me")
    suspend fun getUserInfo(): Response<UserResponse>
}
