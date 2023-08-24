package com.yapp.bol.data.model.group

data class NewGroupApiResponse(
    val id: Int,
    val name: String,
    val description: String,
    val owner: String,
    val organization: String,
    val profileImageUrl: String,
    val accessCode: String,
)
