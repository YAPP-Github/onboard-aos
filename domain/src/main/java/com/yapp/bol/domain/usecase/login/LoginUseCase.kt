package com.yapp.bol.domain.usecase.login

import com.yapp.bol.domain.model.LoginItem
import com.yapp.bol.domain.repository.MockRepository
import com.yapp.bol.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val mockRepository: MockRepository,
) {
    suspend fun execute(
        emitter: RemoteErrorEmitter,
        type: String,
        token: String,
    ): LoginItem? = mockRepository.login(emitter = emitter, type = type, token = token)
}
