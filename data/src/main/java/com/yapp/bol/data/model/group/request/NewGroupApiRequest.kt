package com.yapp.bol.data.model.group.request

data class NewGroupApiRequest(
    val name: String,
    val description: String,
    val organization: String,
    val imageUrl: String,
    val nickname: String,
)
