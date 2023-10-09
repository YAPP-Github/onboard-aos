package com.yapp.bol.domain.usecase.login

import com.yapp.bol.domain.repository.UserRepository
import javax.inject.Inject

class SplashUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun getOnBoard() = userRepository.getOnBoard()
}
