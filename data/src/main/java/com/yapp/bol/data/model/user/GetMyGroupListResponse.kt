package com.yapp.bol.data.model.user

import com.google.gson.annotations.SerializedName

data class GetMyGroupListResponse(
    @SerializedName("contents")
    val groupList: List<MyGroup>,
)
