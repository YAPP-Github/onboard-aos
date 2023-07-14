package com.yapp.bol.domain.model

data class MemberItems(
    val members: List<MemberItem>,
    val cursor: String,
    val hasNext: Boolean,
)
