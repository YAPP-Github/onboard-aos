package com.yapp.bol.data.model.group.response

data class JoinedGroupResponse(
    val contents: List<JoinedGroupDTO>
)

data class JoinedGroupDTO(
    val id: Long,
    val name: String,
    val description: String,
    val organization: String,
    val profileImageUrl: String,
)
