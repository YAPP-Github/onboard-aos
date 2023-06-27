package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.OAuthApiResponse
import com.yapp.bol.data.model.file_upload.FileUploadResponse
import com.yapp.bol.data.model.group.GameApiResponse
import com.yapp.bol.data.model.group.GameDTO
import com.yapp.bol.data.model.group.MemberDTO
import com.yapp.bol.data.model.group.MemberListResponse
import com.yapp.bol.data.model.group.MemberValidApiResponse
import com.yapp.bol.data.model.group.NewGroupApiResponse
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.domain.model.LoginItem
import com.yapp.bol.domain.model.MemberItem
import com.yapp.bol.domain.model.NewGroupItem

internal object MapperToDomain {

    fun OAuthApiResponse?.toDomain(): LoginItem? = this?.toItem()

    private fun OAuthApiResponse.toItem(): LoginItem {
        return LoginItem(
            this.accessToken,
            this.refreshToken,
        )
    }

    private fun NewGroupApiResponse.toItem(): NewGroupItem {
        return NewGroupItem(
            this.id,
            this.name,
            this.description,
            this.owner,
            this.organization,
            this.profileImageUrl,
            this.accessCode
        )
    }

    private fun GameDTO.toItem(): GameItem {
        return GameItem(
            id = this.id,
            name = this.name,
            maxMember = this.maxMember,
            minMember = this.maxMember,
            img = this.img,
        )
    }

    private fun MemberDTO.toItem(): MemberItem {
        return MemberItem(
            id = this.id,
            role = this.role,
            nickname = this.nickname,
            level = this.level,
        )
    }

    fun ApiResult<FileUploadResponse>.fileUploadToDomain(): ApiResult<String> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.url)
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    fun ApiResult<NewGroupApiResponse>.newGroupToDomain(): ApiResult<NewGroupItem> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.toItem())
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    fun ApiResult<GameApiResponse>.gameToDomain(): ApiResult<List<GameItem>> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.list.map { it.toItem() })
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    fun ApiResult<MemberValidApiResponse>.validToDomain(): ApiResult<Boolean> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.isAvailable)
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    fun ApiResult<MemberListResponse>.toDomain(): ApiResult<List<MemberItem>> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.contents.map { it.toItem() })
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }
}
