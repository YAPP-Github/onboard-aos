package com.yapp.bol.presentation.viewmodel.login

import com.yapp.bol.domain.model.JoinedGroupItem

object MyGroupList {
    private var myGroupList: List<JoinedGroupItem>? = null

    fun setMyGroupList(myGroupList: List<JoinedGroupItem>) {
        this.myGroupList = myGroupList
    }

    fun getMyGroupList(): List<JoinedGroupItem>? {
        return myGroupList
    }

    fun findMyGroup(groupId: Long?): JoinedGroupItem? {
        return myGroupList?.find { it.id == groupId }
    }
}
