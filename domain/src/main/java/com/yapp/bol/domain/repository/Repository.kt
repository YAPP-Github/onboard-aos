package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.NewGroupItem
import com.yapp.bol.domain.model.OAuthApiItem
import kotlinx.coroutines.flow.Flow
import java.io.File

interface Repository {

    fun getKakaoMock(token: String): Flow<ApiResult<OAuthApiItem>>

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
}
