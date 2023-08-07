package com.yapp.bol.domain.model

data class GetGroupJoinedItem(
    val groupDetail: GroupDetailItem,
    val nickname: String,
    val hasJoinedGroup: Boolean,
)
