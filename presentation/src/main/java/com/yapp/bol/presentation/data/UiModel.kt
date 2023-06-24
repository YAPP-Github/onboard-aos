package com.yapp.bol.presentation.data

import com.yapp.bol.domain.model.GroupSearchItem

sealed class UiModel {
    data class GroupList(val groupSearchItem: GroupSearchItem) : UiModel()
    object DataNotFound : UiModel()
}
