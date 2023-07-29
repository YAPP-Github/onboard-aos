package com.yapp.bol.domain.usecase.auth

import com.yapp.bol.domain.repository.AuthRepository

class DeleteRefreshTokenUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke() = repository.deleteRefreshToken()
}
