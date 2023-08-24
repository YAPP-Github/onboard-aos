package com.yapp.bol.data.model.group

data class NewGroupApiRequest(
    val name: String,
    val description: String,
    val organization: String,
    val profileImageUrl: String,
    val nickname: String,
)
