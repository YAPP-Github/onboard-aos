package com.yapp.bol.domain.model

data class UserRankListItem(
    val userRankItemList: List<UserRankItem>,
)

data class UserRankItem(
    val id: Long,
    val rank: Int?,
    val name: String,
    val score: Int?,
    val playCount: Int?,
    val isChangeRecent: Boolean,
    val role: Role
)

sealed class Role(val string: String) {
    object OWNER : Role("OWNER")
    object HOST : Role("HOST")
    object GUEST : Role("GUEST")
}
