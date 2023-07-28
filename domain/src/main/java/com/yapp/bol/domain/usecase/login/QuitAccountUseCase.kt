package com.yapp.bol.domain.usecase.login

import com.yapp.bol.domain.repository.Repository
import javax.inject.Inject

class QuitAccountUseCase @Inject constructor(
    private val repository: Repository,
) {

    operator fun invoke() = repository.quitAccount()
}
