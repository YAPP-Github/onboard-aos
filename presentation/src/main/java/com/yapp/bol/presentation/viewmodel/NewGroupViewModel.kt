package com.yapp.bol.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.usecase.login.NewGroupUseCase
import com.yapp.bol.domain.utils.ErrorType
import com.yapp.bol.domain.utils.RemoteErrorEmitter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class NewGroupViewModel @Inject constructor(
    private val newGroupUseCase: NewGroupUseCase
): ViewModel(), RemoteErrorEmitter {

    private val _imageFile = MutableLiveData(File(""))
    val imageFile = _imageFile

    fun postFileUpload(token: String) {
        viewModelScope.launch {
            imageFile.value?.let {
                newGroupUseCase.postFileUpload(this@NewGroupViewModel,token, it)
            }
        }
    }

    fun updateImageFile(file: File) {
        _imageFile.value = file
    }

    override fun onError(msg: String) {
        TODO("Not yet implemented")
    }

    override fun onError(errorType: ErrorType) {
        TODO("Not yet implemented")
    }

}
