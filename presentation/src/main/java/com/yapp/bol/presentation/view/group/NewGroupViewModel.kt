package com.yapp.bol.presentation.view.group

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.NewGroupItem
import com.yapp.bol.domain.usecase.login.NewGroupUseCase
import com.yapp.bol.presentation.utils.Constant.EMPTY_STRING
import com.yapp.bol.presentation.utils.checkedApiResult
import com.yapp.bol.presentation.utils.checkedEmptyValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class NewGroupViewModel @Inject constructor(
    private val newGroupUseCase: NewGroupUseCase
) : ViewModel() {

    private val _groupName = MutableLiveData(EMPTY_STRING)
    val groupName = _groupName

    private val _groupDescription = MutableLiveData(EMPTY_STRING)
    val groupDescription = _groupDescription

    private val _successGroupDate = MutableLiveData<NewGroupItem?>(null)
    val successGroupDate = _successGroupDate

    private var groupOrganization = EMPTY_STRING
    private var imageFile = File(EMPTY_STRING)

    fun createNewGroup(token: String, nickName: String) {
        viewModelScope.launch {
            val imageUrl = withContext(Dispatchers.IO) { postFileUpload(token) }
            postCreateGroup(nickName, imageUrl)
        }
    }

    private suspend fun postFileUpload(token: String): String {
        var imageUrl = EMPTY_STRING
        newGroupUseCase.postFileUpload(token, imageFile).collectLatest {
            checkedApiResult(
                apiResult = it,
                success = { data -> imageUrl = data },
                error = { throwable -> throw throwable })
        }
        return imageUrl
    }

    private suspend fun postCreateGroup(nickName: String, imageUrl: String) {
        newGroupUseCase.postCreateGroup(
            name = groupName.value ?: EMPTY_STRING,
            description = groupDescription.value ?: EMPTY_STRING,
            organization = groupOrganization,
            profileImageUrl = imageUrl,
            nickname = nickName,
        ).collectLatest {
            checkedApiResult(
                apiResult = it,
                success = { data -> _successGroupDate.value = data },
                error = { throwable -> throw throwable })
        }
    }

    fun updateGroupInfo(type: String, value: String) {
        when (type) {
            NEW_GROUP_NAME -> _groupName.value = value
            NEW_GROUP_DESCRIPTION -> _groupDescription.value = value
            NEW_GROUP_ORGANIZATION -> groupOrganization = value
        }
    }

    fun checkedCompleteButtonActivation(): Boolean {
        return groupName.value.checkedEmptyValue() && groupDescription.value.checkedEmptyValue()
    }

    fun updateImageFile(file: File) {
        imageFile = file
    }

    companion object {
        const val NEW_GROUP_NAME = "newGroupName"
        const val NEW_GROUP_DESCRIPTION = "newGroupDescription"
        const val NEW_GROUP_ORGANIZATION = "newGroupOrganization"
    }
}
