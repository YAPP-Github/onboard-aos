package com.yapp.bol.domain.model.user.group

import com.yapp.bol.domain.model.OwnerItem

// todo name 수정 필요
data class GetGroupItem(
    val accessCode: String,
    val description: String,
    val id: Int,
    val memberCount: Int,
    val name: String,
    val organization: String,
    val owner: OwnerItem,
    val profileImageUrl: String,
){
    val ownerNickname = owner.nickname
}
