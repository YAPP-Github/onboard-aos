package com.yapp.bol.domain.model

data class GetGroupJoinedItem(
    val groupDetail: GroupDetailItem,
    val gameList: List<GameItem>,
    val nickname: String,
    val hasJoinedGroup: Boolean,
) {

    private fun getMaxTwoGameList(): List<GameItem> {
        return gameList.sortedByDescending { it.maxMember }.take(2)
    }

    fun getFirstMaxGame() = getMaxTwoGameList().firstOrNull()
    fun getSecondMaxGame() = getMaxTwoGameList().getOrNull(1)
}
