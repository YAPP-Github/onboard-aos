package com.yapp.bol.presentation.view.match

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yapp.bol.presentation.model.MemberItem
import com.yapp.bol.presentation.utils.Constant.GAME_RESULT_RECORD
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MatchViewModel @Inject constructor() : ViewModel() {

    private val _toolBarTitle = MutableLiveData(GAME_RESULT_RECORD)
    val toolBarTitle = _toolBarTitle

    private val _members = MutableLiveData<List<MemberItem>>(listOf())
    val members = _members

    private val tempList = arrayListOf<MemberItem>()

    private val _selectMembers = MutableStateFlow(listOf<MemberItem>())
    val selectMembers = _selectMembers.asStateFlow()

    fun updateToolBarTitle(title: String) {
        _toolBarTitle.value = title
    }

    fun getMembers() {
        _members.value = testMembers
    }

    fun addSelectMembers(memberItem: MemberItem) {
        tempList.add(memberItem)
        _selectMembers.value = createNewMembers()
    }

    fun removeSelectMembers(memberItem: MemberItem) {
        tempList.remove(memberItem)
        _selectMembers.value = createNewMembers()
    }

    private fun createNewMembers(): List<MemberItem> {
        return List(tempList.size) {
            tempList[it]
        }
    }

    companion object {
        val testMembers = List(10) {
            MemberItem(it,"$it. Test", 1)
        }
    }
}
