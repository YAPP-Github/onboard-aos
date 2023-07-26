package com.yapp.bol.data.model.group.response

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

data class OwnerDTO(
    val id: Long,
    val role: String,
    val nickname: String,
    val level: Int
)
