package com.yapp.bol.data.model.group

data class GroupDetailResponse(
    val id: Long,
    val name: String,
    val description: String,
    val organization: String,
    val profileImageUrl: String,
    val accessCode: String,
    val memberCount: Int,
    val owner: OwnerDTO
)
