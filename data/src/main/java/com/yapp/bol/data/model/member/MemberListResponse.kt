package com.yapp.bol.data.model.member

import com.yapp.bol.data.model.group.MemberDTO

data class MemberListResponse(
    val contents: List<MemberDTO>,
    val cursor: String,
    val hasNext: Boolean,
)
