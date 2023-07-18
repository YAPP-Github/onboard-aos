package com.yapp.bol.presentation.viewmodel.login

import android.util.Log
import com.yapp.bol.domain.model.user.group.MyGroupItem

object MyGroupList {
    private var myGroupList: List<MyGroupItem>? = null

    fun setMyGroupList(myGroupList: List<MyGroupItem>) {
        this.myGroupList = myGroupList
    }

    fun getMyGroupList(): List<MyGroupItem>? {
        return myGroupList
    }

    fun findMyGroup(groupId: Int?): MyGroupItem? {
        return myGroupList?.find { it.id == groupId }
    }
}
