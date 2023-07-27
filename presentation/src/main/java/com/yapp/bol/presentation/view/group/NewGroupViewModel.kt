package com.yapp.bol.presentation.view.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.NewGroupItem
import com.yapp.bol.domain.usecase.login.NewGroupUseCase
import com.yapp.bol.presentation.utils.Constant.EMPTY_STRING
import com.yapp.bol.presentation.utils.checkedApiResult
import com.yapp.bol.presentation.utils.isInputTextValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class NewGroupViewModel @Inject constructor(
    private val newGroupUseCase: NewGroupUseCase
) : ViewModel() {

    private val _groupName = MutableLiveData(EMPTY_STRING)
    val groupName: LiveData<String> = _groupName

    private val _groupDescription = MutableLiveData(EMPTY_STRING)
    val groupDescription: LiveData<String> = _groupDescription

    private val _successGroupDate = MutableLiveData<NewGroupItem?>(null)
    val successGroupDate: LiveData<NewGroupItem?> = _successGroupDate

    private val _groupRandomImage = MutableLiveData("")
    val groupRandomImage: LiveData<String> = _groupRandomImage

    private var groupOrganization = EMPTY_STRING
    private var imageFile = File(EMPTY_STRING)

    val isCompleteButtonActivation
        get() = isInputTextValid(groupName.value) && isInputTextValid(groupDescription.value)

    init {
        getRandomImage()
    }

    fun createNewGroup(nickName: String) {
        viewModelScope.launch {
            val imageUrl = withContext(Dispatchers.IO) { postFileUpload() }
            postCreateGroup(nickName, imageUrl)
        }
    }

    private suspend fun postFileUpload(): String {
        var imageUrl = EMPTY_STRING
        newGroupUseCase.postFileUpload(imageFile).collectLatest {
            checkedApiResult(
                apiResult = it,
                success = { data -> imageUrl = data },
            )
        }
        return imageUrl
    }

    private suspend fun postCreateGroup(nickName: String, imageUrl: String) {
        newGroupUseCase.postCreateGroup(
            name = groupName.value ?: EMPTY_STRING,
            description = groupDescription.value ?: EMPTY_STRING,
            organization = groupOrganization,
            imageUrl = imageUrl,
            nickname = nickName,
        ).collectLatest {
            checkedApiResult(
                apiResult = it,
                success = { data -> _successGroupDate.value = data },
            )
        }
    }

    fun getRandomImage() {
        viewModelScope.launch {
            newGroupUseCase.getRandomImage().collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { data ->
                        _groupRandomImage.value = data
                        convertUrlToFile(data)
                    },
                )
            }
        }
    }

    fun updateGroupInfo(type: String, value: String) {
        when (type) {
            NEW_GROUP_NAME -> _groupName.value = value
            NEW_GROUP_DESCRIPTION -> _groupDescription.value = value
            NEW_GROUP_ORGANIZATION -> groupOrganization = value
        }
    }

    fun updateImageFile(file: File) {
        imageFile = file
    }

    private fun convertUrlToFile(imageUrl: String) {
        viewModelScope.launch {
            val file = withContext(Dispatchers.IO) {
                val url = URL(imageUrl)
                val connection = url.openConnection()
                connection.connect()
                val inputStream: InputStream = BufferedInputStream(url.openStream())

                val imageFile = File.createTempFile("image", ".jpg")
                val outputStream = FileOutputStream(imageFile)

                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                outputStream.close()
                inputStream.close()

                imageFile
            }
            updateImageFile(file)
        }
    }

    companion object {
        const val NEW_GROUP_NAME = "newGroupName"
        const val NEW_GROUP_DESCRIPTION = "newGroupDescription"
        const val NEW_GROUP_ORGANIZATION = "newGroupOrganization"
    }
}
