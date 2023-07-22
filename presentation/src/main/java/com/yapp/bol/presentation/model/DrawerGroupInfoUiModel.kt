package com.yapp.bol.presentation.model

import com.yapp.bol.domain.model.JoinedGroupItem

sealed class DrawerGroupInfoUiModel {
    data class CurrentGroupInfo(val joinedGroupItem: JoinedGroupItem) : DrawerGroupInfoUiModel()
    data class OtherGroupInfo(val joinedGroupItem: JoinedGroupItem) : DrawerGroupInfoUiModel()
}
