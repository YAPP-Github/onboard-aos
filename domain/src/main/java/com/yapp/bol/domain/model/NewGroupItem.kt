package com.yapp.bol.domain.model

import java.io.Serializable

data class NewGroupItem(
    val id: Int,
    val name: String,
    val description: String,
    val owner: String,
    val organization: String,
    val imageUrl: String,
    val accessCode: String,
) : Serializable
