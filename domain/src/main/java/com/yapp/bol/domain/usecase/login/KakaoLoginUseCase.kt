package com.yapp.bol.domain.usecase.login

import com.yapp.bol.domain.model.MockApiItem
import com.yapp.bol.domain.repository.MockRepository
import com.yapp.bol.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val mockRepository: MockRepository,
) {
    suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter, token: String): MockApiItem? =
        mockRepository.getKakaoMock(remoteErrorEmitter, token = token)
}
