package com.yapp.bol.data.remote

import com.yapp.bol.data.model.rank.UserRankApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RankApi {

    @GET("/v1/group/{groupId}/game/{gameId}")
    suspend fun getUserRank(
        @Path("groupId") groupId: Int,
        @Path("gameId") gameId: Int,
    ): Response<UserRankApiResponse>
}
