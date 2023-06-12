package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.MockApiResponse
import com.yapp.bol.domain.model.LoginItem
import com.yapp.bol.data.model.OAuthApiResponse
import com.yapp.bol.data.model.file_upload.FileUploadResponse
import com.yapp.bol.data.model.group.NewGroupApiResponse
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.NewGroupItem
import com.yapp.bol.domain.model.OAuthApiItem

object MapperToDomain {
    fun MockApiResponse?.toDomain(): LoginItem? = this?.toItem()

    private fun MockApiResponse.toItem(): LoginItem {
        return LoginItem(
            this.accessToken,
            this.refreshToken,
        )
    }

    fun NewGroupApiResponse.toItem(): NewGroupItem {
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

    fun ApiResult<OAuthApiResponse>.oAuthToDomain(): ApiResult<OAuthApiItem> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.toItem())
            is ApiResult.Error -> ApiResult.Error(exception)
        }
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
}
