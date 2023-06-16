package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.domain.model.LoginItem
import com.yapp.bol.domain.model.NewGroupItem
import kotlinx.coroutines.flow.Flow
import java.io.File

interface Repository {

    suspend fun login(type: String, token: String): LoginItem?

    fun postFileUpload(
        token: String,
        file: File
    ): Flow<ApiResult<String>>

    fun postCreateGroup(
        name: String,
        description: String,
        organization: String,
        profileImageUrl: String,
        nickname: String
    ): Flow<ApiResult<NewGroupItem>>

    fun getGameList(groupId: String): Flow<ApiResult<List<GameItem>>>
}
