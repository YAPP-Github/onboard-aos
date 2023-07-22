package com.yapp.bol.data.remote

import com.yapp.bol.data.model.match.MatchApiRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface MatchApi {

    @POST("v1/match")
    suspend fun postMatch(
        @Body matchApiRequest: MatchApiRequest
    )
}
