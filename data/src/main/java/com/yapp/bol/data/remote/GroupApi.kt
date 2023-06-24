package com.yapp.bol.data.remote

import com.yapp.bol.data.model.group.GroupSearchApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GroupApi {
    @GET("/v1/group/search")
    suspend fun getGroupSearchResult(
        @Query("name") name: String,
        @Query("pageNumber") page: String,
        @Query("pageSize") pageSize: String,
    ): Response<GroupSearchApiResponse>
}
