package com.yapp.bol.domain.usecase.login

import com.yapp.bol.domain.model.LoginItem
import com.yapp.bol.domain.repository.AuthRepository
import com.yapp.bol.domain.repository.TermsRepository
import com.yapp.bol.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val termsRepository: TermsRepository,
    private val userRepository: UserRepository,
) {
    suspend fun execute(
        type: String,
        token: String,
    ): LoginItem? = authRepository.login(type = type, token = token)

    fun getTerms() = termsRepository.getTerms()

    suspend fun postTerms(agree: List<String>, disagree: List<String>) = termsRepository.postTerms(agree, disagree)

    fun getOnBoard() = userRepository.getOnBoard()

    suspend fun putUserName(nickName: String) = userRepository.putUserName(nickName)
}
