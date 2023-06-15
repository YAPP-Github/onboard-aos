package com.yapp.bol.presentation.view.group.search

import com.yapp.bol.domain.model.GroupSearchItem

sealed class GroupSearchUiModel {
    data class GroupList(val groupSearchItem: GroupSearchItem) : GroupSearchUiModel()
    object DataNotFound : GroupSearchUiModel()
}
