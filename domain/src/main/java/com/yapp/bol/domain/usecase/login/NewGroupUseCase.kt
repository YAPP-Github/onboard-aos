package com.yapp.bol.domain.usecase.login

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.NewGroupItem
import com.yapp.bol.domain.repository.FileRepository
import com.yapp.bol.domain.repository.GroupRepository
import com.yapp.bol.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class NewGroupUseCase @Inject constructor(
    private val fileRepository: FileRepository,
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
) {
    fun postFileUpload(file: File): Flow<ApiResult<String>> {
        return fileRepository.postFileUpload(file)
    }

    fun postCreateGroup(
        name: String,
        description: String,
        organization: String,
        imageUrl: String,
        nickname: String,
    ): Flow<ApiResult<NewGroupItem>> {
        return groupRepository.postCreateGroup(name, description, organization, imageUrl, nickname)
    }

    fun getRandomImage() = groupRepository.getGroupDefaultImage()

    fun getUserInfo() = userRepository.getUserInfo()
}
