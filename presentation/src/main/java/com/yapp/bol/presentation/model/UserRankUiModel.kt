package com.yapp.bol.presentation.model

import com.yapp.bol.domain.model.UserRankItem

sealed class UserRankUiModel {
    data class UserRank1to3(val userRankItemList: List<UserRankItem>) : UserRankUiModel()
    data class UserRankAfter4(val userRankItem: UserRankItem) : UserRankUiModel()
    object UserRankPadding : UserRankUiModel()
}
