package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.BaseItem
import com.yapp.bol.domain.model.CheckGroupJoinByAccessCodeItem
import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.domain.model.LoginItem
import com.yapp.bol.domain.model.MatchItem
import com.yapp.bol.domain.model.MemberItems
import com.yapp.bol.domain.model.NewGroupItem
import com.yapp.bol.domain.model.TermsList
import com.yapp.bol.domain.model.user.UserItem
import kotlinx.coroutines.flow.Flow
import java.io.File

interface Repository {

    suspend fun login(type: String, token: String): LoginItem?

    fun postFileUpload(file: File): Flow<ApiResult<String>>

    fun postCreateGroup(
        name: String,
        description: String,
        organization: String,
        imageUrl: String,
        nickname: String
    ): Flow<ApiResult<NewGroupItem>>

    fun getGameList(groupId: Int): Flow<ApiResult<List<GameItem>>>

    fun getValidateNickName(
        groupId: Int,
        nickname: String,
    ): Flow<ApiResult<Boolean>>

    fun getMemberList(
        groupId: Int,
        pageSize: Int,
        cursor: String?,
        nickname: String?,
    ): Flow<ApiResult<MemberItems>>

    suspend fun postGuestMember(groupId: Int, nickname: String)

    fun geTerms(): Flow<ApiResult<TermsList>>

    suspend fun postTerms(agree: List<String>, disagree: List<String>)

    fun getOnBoard(): Flow<ApiResult<List<String>>>

    fun getRandomImage(): Flow<ApiResult<String>>

    fun joinGroup(
        groupId: String,
        accessCode: String,
        nickname: String,
    ): Flow<ApiResult<BaseItem>>

    fun checkGroupJoinAccessCode(
        groupId: String,
        accessCode: String,
    ): Flow<ApiResult<CheckGroupJoinByAccessCodeItem>>

    suspend fun putUserName(nickName: String)

    suspend fun postMatch(matchItem: MatchItem)

    fun getUserInfo(): Flow<ApiResult<UserItem>>
}
