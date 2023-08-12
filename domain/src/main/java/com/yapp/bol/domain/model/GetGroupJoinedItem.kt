package com.yapp.bol.domain.model

data class GetGroupJoinedItem(
    val groupDetail: GroupDetailItem,
    val gameList: List<GetGroupGame>,
    val nickname: String,
    val hasJoinedGroup: Boolean,
) {

    private fun getMaxTwoGameList(): List<GetGroupGame> {
        return gameList.sortedByDescending { it.maxPlayGameMember }.take(2)
    }

    fun getFirstMaxGame() = getMaxTwoGameList().first()
    fun getSecondMaxGame() = getMaxTwoGameList()[1]
}
