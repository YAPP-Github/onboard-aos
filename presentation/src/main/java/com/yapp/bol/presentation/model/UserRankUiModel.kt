package com.yapp.bol.presentation.model

import com.yapp.bol.domain.model.UserRankItem

sealed class UserRankUiModel {
    data class UserRank1to3(val itemList: List<HomeUserRankItem>) : UserRankUiModel()
    data class UserRankAfter4(
        val item: HomeUserRankItem
    ) : UserRankUiModel()
    object UserRankPadding : UserRankUiModel()
}

data class HomeUserRankItem(
    val userRankItem: UserRankItem,
    val isMe: Boolean,
)
