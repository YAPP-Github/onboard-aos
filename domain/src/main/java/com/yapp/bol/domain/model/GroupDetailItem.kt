package com.yapp.bol.domain.model

data class GroupDetailItem(
    val id: Long,
    val name: String,
    val description: String,
    val organization: String,
    val profileImageUrl: String,
    val accessCode: String,
    val memberCount: Int,
    val owner: OwnerItem,
) {
    val ownerNickname = owner.nickname
}

data class OwnerItem(
    val id: Long,
    val role: String,
    val nickname: String,
    val level: Int,
)
