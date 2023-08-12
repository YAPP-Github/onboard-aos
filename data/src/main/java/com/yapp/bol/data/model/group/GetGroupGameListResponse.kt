package com.yapp.bol.data.model.group

import com.google.gson.annotations.SerializedName

data class GetGroupGameListResponse(
    @SerializedName("list")
    val gameList: List<GetGroupGame>,
)

data class GetGroupGame(
    val id: Int,
    @SerializedName("img")
    val gameImageUrl: String,
    val maxMember: Int,
    val minMember: Int,
    val name: String,
)
