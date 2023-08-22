package com.yapp.bol.presentation.mapper

import com.yapp.bol.domain.model.MemberItem
import com.yapp.bol.presentation.model.MemberInfo

object MatchMapper {

    fun MemberItem.toPresentation(): MemberInfo {
        return MemberInfo(
            id = this.id,
            role = this.role,
            nickname = this.nickname,
            level = this.level,
        )
    }
}
