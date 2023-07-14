package com.yapp.bol.domain.usecase.login

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.LoginItem
import com.yapp.bol.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: Repository,
) {
    suspend fun execute(
        type: String,
        token: String,
    ): LoginItem? = repository.login(type = type, token = token)

    fun getTerms() = repository.geTerms()

    suspend fun postTerms(agree: List<String>, disagree: List<String>) = repository.postTerms(agree,disagree)

    fun getOnBoard() = repository.getOnBoard()
}
