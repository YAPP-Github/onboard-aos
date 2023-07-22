package com.yapp.bol.data.model.group.response

data class GroupSearchApiResponse(
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
