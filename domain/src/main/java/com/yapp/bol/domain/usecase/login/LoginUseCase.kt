package com.yapp.bol.domain.usecase.login

import com.yapp.bol.domain.model.LoginItem
import com.yapp.bol.domain.repository.Repository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: Repository,
) {
    suspend fun execute(
        type: String,
        token: String,
    ): LoginItem? = repository.login(type = type, token = token)
}
