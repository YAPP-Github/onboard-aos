package com.yapp.bol.data.model.rank

import com.google.gson.annotations.SerializedName

data class UserRankApiResponse(
    val contents: List<UserRankDTO>,
)

data class UserRankDTO(
    val id: Long,
    val rank: Int,
    val name: String,
    @SerializedName("winningPercentage")
    val winRate: Double,
    val playCount: Int,
)
