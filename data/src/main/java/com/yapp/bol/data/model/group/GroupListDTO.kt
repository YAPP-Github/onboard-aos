package com.yapp.bol.data.model.group

data class GroupListDTO(
    val id: Int,
    val name: String,
    val description: String,
    val organization: String,
    val profileImageUrl: String,
    val memberCount: Int
)
