package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.base.BaseResponse
import com.yapp.bol.data.model.group.MemberDTO
import com.yapp.bol.data.model.group.MemberListResponse
import com.yapp.bol.data.model.group.response.CheckGroupJoinByAccessCodeResponse
import com.yapp.bol.data.model.group.response.GameApiResponse
import com.yapp.bol.data.model.group.response.GameResponse
import com.yapp.bol.data.model.group.response.GroupSearchApiResponse
import com.yapp.bol.data.model.group.response.ImageFileUploadResponse
import com.yapp.bol.data.model.group.response.MemberValidApiResponse
import com.yapp.bol.data.model.group.response.NewGroupApiResponse
import com.yapp.bol.data.model.group.response.RandomImageResponse
import com.yapp.bol.data.model.login.LoginResponse
import com.yapp.bol.data.model.login.OnBoardResponse
import com.yapp.bol.data.model.login.TermsDTO
import com.yapp.bol.data.model.login.TermsResponse
import com.yapp.bol.data.model.match.MatchApiRequest
import com.yapp.bol.data.model.match.MatchMemberDTO
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.BaseItem
import com.yapp.bol.domain.model.CheckGroupJoinByAccessCodeItem
import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.domain.model.GroupItem
import com.yapp.bol.domain.model.GroupSearchItem
import com.yapp.bol.domain.model.LoginItem
import com.yapp.bol.domain.model.MatchItem
import com.yapp.bol.domain.model.MatchMemberItem
import com.yapp.bol.domain.model.MemberItem
import com.yapp.bol.domain.model.MemberItems
import com.yapp.bol.domain.model.NewGroupItem
import com.yapp.bol.domain.model.TermsItem
import com.yapp.bol.domain.model.TermsList

internal object MapperToDomain {

    fun LoginResponse?.toDomain(): LoginItem? = this?.toItem()

    private fun LoginResponse.toItem(): LoginItem {
        return LoginItem(
            this.accessToken,
            this.refreshToken,
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

    private fun GameResponse.toItem(): GameItem {
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
            level = this.level
        )
    }

    private fun TermsDTO.toItem(): TermsItem {
        return TermsItem(
            code = this.code,
            title = this.title,
            url = this.url,
            isRequired = this.isRequired
        )
    }

    fun ApiResult<ImageFileUploadResponse>.fileUploadToDomain(): ApiResult<String> {
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

    fun ApiResult<MemberListResponse>.memberListToDomain(): ApiResult<MemberItems> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(
                MemberItems(
                    members = data.contents.map { it.toItem() },
                    cursor = data.cursor,
                    hasNext = data.hasNext
                )
            )

            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    fun ApiResult<TermsResponse>.toTermsDomain(): ApiResult<TermsList> {
        return when (this) {
            is ApiResult.Success -> {
                ApiResult.Success(TermsList(data.contents.map { it.toItem() }))
            }

            is ApiResult.Error -> {
                ApiResult.Error(exception)
            }
        }
    }

    fun ApiResult<OnBoardResponse>.toBoardDomain(): ApiResult<List<String>> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.onboarding)
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    fun ApiResult<RandomImageResponse>.toImageDomain(): ApiResult<String> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.url)
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    fun ApiResult<BaseResponse>.mapperToBaseItem(): ApiResult<BaseItem> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(BaseItem(data.code, data.message))
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    fun ApiResult<CheckGroupJoinByAccessCodeResponse>.mapperToCheckGroupJoinByAccessCodeItem():
        ApiResult<CheckGroupJoinByAccessCodeItem> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(CheckGroupJoinByAccessCodeItem(data.isNewMember))
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    private fun MatchMemberItem.toMatchItem(): MatchMemberDTO {
        return MatchMemberDTO(
            this.memberId,
            this.score,
            this.ranking
        )
    }

    fun MatchItem.toMatchDomain(): MatchApiRequest {
        return MatchApiRequest(
            this.gameId,
            this.groupId,
            this.matchedDate,
            this.matchMembers.map { it.toMatchItem() }
        )
    }
}
