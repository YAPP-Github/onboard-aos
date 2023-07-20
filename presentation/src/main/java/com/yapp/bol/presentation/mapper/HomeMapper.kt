package com.yapp.bol.presentation.mapper

import com.yapp.bol.domain.model.UserRankItem
import com.yapp.bol.domain.model.UserRankListItem
import com.yapp.bol.presentation.model.UserRankUiModel
import com.yapp.bol.presentation.utils.config.HomeConfig

object HomeMapper {

    fun UserRankListItem.toUserRankUiModel(): List<UserRankUiModel> {
        val userRank1To3 = mutableListOf<UserRankItem>()
        val userRankAfter4 = mutableListOf<UserRankUiModel>()
        val resultList = mutableListOf<UserRankUiModel>()

        if (userRankItemList.size <= HomeConfig.NO_RANK_THRESHOLD) { return resultList }

        userRankItemList.forEachIndexed { index, userRankItem ->
            if (index < HomeConfig.USER_RV_1_TO_3_UI_RANK_THRESHOLD) {
                userRank1To3.add(userRankItem)
            } else {
                userRankAfter4.add(UserRankUiModel.UserRankAfter4(userRankItem))
            }
        }

        resultList.add(UserRankUiModel.UserRank1to3(userRank1To3))
        resultList.addAll(userRankAfter4)

        return resultList
    }

}
