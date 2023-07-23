package com.yapp.bol.data.remote

import com.yapp.bol.data.model.user.GetMyGroupListResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {

    @GET("v1/user/me/group")
    suspend fun getMyGroupList(): Response<GetMyGroupListResponse>
}
