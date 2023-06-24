package com.yapp.bol.domain.model

data class GroupSearchItem(
    val id: Int,
    val name: String,
    val description: String,
    val organization: String,
    val profileImageUrl: String,
    val memberCount: Int,
)
