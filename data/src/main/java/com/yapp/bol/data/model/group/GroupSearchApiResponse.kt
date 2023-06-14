package com.yapp.bol.data.model.group

data class GroupListResponse(
    val groups: GroupContent
)

data class GroupContent(
    val content: List<GroupListItem>,
    val hasNext: Boolean
)

data class GroupListItem(
    val id: Int,
    val name: String,
    val description: String,
    val organization: String,
    val profileImageUrl: String,
    val memberCount: Int
)
