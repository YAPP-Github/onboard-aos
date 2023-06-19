package com.yapp.bol.presentation.data

import com.yapp.bol.domain.model.GroupItem

sealed class GroupSearchUiModel {
    data class GroupList(val groupItem: GroupItem) : GroupSearchUiModel()
    data class DataNotFound(val keyword: String) : GroupSearchUiModel()
}
