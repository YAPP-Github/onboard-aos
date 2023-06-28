package com.yapp.bol.domain.model

data class GroupSearchItem(
    val hasNext: Boolean,
    val groupItemList: List<GroupItem>,
)

data class GroupItem(
    val id: Int,
    val name: String,
    val description: String,
    val organization: String,
    val profileImageUrl: String,
    val memberCount: Int,
)
