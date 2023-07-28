package com.yapp.bol.data.model.group.response

import com.yapp.bol.domain.model.OwnerItem

data class GetGroupResponse(
    val accessCode: String,
    val description: String,
    val id: Int,
    val memberCount: Int,
    val name: String,
    val organization: String,
    val owner: OwnerItem,
    val profileImageUrl: String,
)
