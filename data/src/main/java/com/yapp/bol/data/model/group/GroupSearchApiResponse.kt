package com.yapp.bol.data.model.group

data class GroupSearchApiResponse(
    val content: List<GroupListDTO>,
    val hasNext: Boolean
)
