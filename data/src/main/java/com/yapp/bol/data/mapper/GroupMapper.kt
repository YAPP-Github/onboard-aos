package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.group.CheckGroupJoinByAccessCodeResponse
import com.yapp.bol.data.model.group.GroupDetailResponse
import com.yapp.bol.data.model.group.GroupSearchApiResponse
import com.yapp.bol.data.model.group.NewGroupApiResponse
import com.yapp.bol.data.model.group.OwnerDTO
import com.yapp.bol.data.model.group.RandomImageResponse
import com.yapp.bol.data.model.group.UserRankApiResponse
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.CheckGroupJoinByAccessCodeItem
import com.yapp.bol.domain.model.GroupDetailItem
import com.yapp.bol.domain.model.GroupItem
import com.yapp.bol.domain.model.GroupSearchItem
import com.yapp.bol.domain.model.NewGroupItem
import com.yapp.bol.domain.model.OwnerItem
import com.yapp.bol.domain.model.Role
import com.yapp.bol.domain.model.UserRankItem
import com.yapp.bol.domain.model.UserRankListItem

object GroupMapper {

    fun ApiResult<NewGroupApiResponse>.newGroupToDomain(): ApiResult<NewGroupItem> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.toItem())
            is ApiResult.Error -> ApiResult.Error(exception)
        }
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

    fun ApiResult<GroupDetailResponse>.toDetailItem(): ApiResult<GroupDetailItem> =
        when (this) {
            is ApiResult.Success -> ApiResult.Success(this.data.toItem())
            is ApiResult.Error -> ApiResult.Error(exception)
        }

    private fun GroupDetailResponse.toItem(): GroupDetailItem =
        GroupDetailItem(
            id = this.id,
            name = this.name,
            description = this.description,
            organization = this.organization,
            profileImageUrl = this.profileImageUrl,
            accessCode = this.accessCode,
            memberCount = this.memberCount,
            owner = this.owner.toItem()
        )

    private fun OwnerDTO.toItem(): OwnerItem =
        OwnerItem(
            id = this.id,
            role = this.role,
            nickname = this.nickname,
            level = this.level
        )

    fun ApiResult<UserRankApiResponse>.toUserRankItem(): ApiResult<UserRankListItem> =
        when (this) {
            is ApiResult.Success -> ApiResult.Success(
                UserRankListItem(
                    userRankItemList = data.toDomain()
                )
            )
            is ApiResult.Error -> ApiResult.Error(exception)
        }

    private fun UserRankApiResponse.toDomain(): List<UserRankItem> =
        this.contents.map { userRankDTO ->
            UserRankItem(
                id = userRankDTO.id,
                rank = userRankDTO.rank,
                name = userRankDTO.name,
                score = userRankDTO.score,
                playCount = userRankDTO.playCount,
                isChangeRecent = userRankDTO.isChangeRecent,
                role = when (userRankDTO.role) {
                    Role.GUEST.string -> Role.GUEST
                    Role.HOST.string -> Role.HOST
                    else -> Role.OWNER
                }
            )
        }

    fun ApiResult<GroupSearchApiResponse>.toDomain(): ApiResult<GroupSearchItem> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(
                GroupSearchItem(
                    hasNext = this.data.hasNext,
                    groupItemList = data.toItem(),
                )
            )

            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    private fun GroupSearchApiResponse.toItem(): List<GroupItem> {
        return this.content.map { groupItem ->
            GroupItem(
                id = groupItem.id,
                name = groupItem.name,
                description = groupItem.description,
                organization = groupItem.organization,
                profileImageUrl = groupItem.profileImageUrl,
                memberCount = groupItem.memberCount
            )
        }
    }

    fun ApiResult<CheckGroupJoinByAccessCodeResponse>.mapperToCheckGroupJoinByAccessCodeItem():
        ApiResult<CheckGroupJoinByAccessCodeItem> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(CheckGroupJoinByAccessCodeItem(data.isNewMember))
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    fun ApiResult<RandomImageResponse>.toImageDomain(): ApiResult<String> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.url)
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }
}
