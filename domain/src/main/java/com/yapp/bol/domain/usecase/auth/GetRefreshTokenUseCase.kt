package com.yapp.bol.domain.usecase.auth

import com.yapp.bol.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRefreshTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(): Flow<String> = authRepository.refreshToken
}
