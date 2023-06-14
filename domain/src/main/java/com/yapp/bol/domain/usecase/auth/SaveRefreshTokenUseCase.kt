package com.yapp.bol.domain.usecase.auth

import com.yapp.bol.domain.repository.AuthRepository
import javax.inject.Inject

class SaveRefreshTokenUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(token: String) {
        repository.saveRefreshToken(token)
    }
}
