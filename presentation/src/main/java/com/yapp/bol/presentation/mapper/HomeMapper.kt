package com.yapp.bol.presentation.mapper

import com.yapp.bol.domain.model.UserRankItem
import com.yapp.bol.domain.model.UserRankListItem
import com.yapp.bol.presentation.model.UserRankUiModel
import com.yapp.bol.presentation.utils.config.HomeConfig

object HomeMapper {

    fun UserRankListItem.toUserRankUiModel(): List<UserRankUiModel> {
        val userRank1To3 = mutableListOf<UserRankItem>()
        val userRankAfter4 = mutableListOf<UserRankUiModel>()

        userRankItemList.map { item ->
            if (item.rank <= HomeConfig.USER_RV_1_TO_3_UI_RANK_THRESHOLD) {
                userRank1To3.add(item)
            } else {
                userRankAfter4.add(UserRankUiModel.UserRankAfter4(item))
            }
        }

        val resultList = mutableListOf<UserRankUiModel>()
        resultList.add(UserRankUiModel.UserRank1to3(userRank1To3))
        resultList.addAll(userRankAfter4)

        return resultList
    }

}
