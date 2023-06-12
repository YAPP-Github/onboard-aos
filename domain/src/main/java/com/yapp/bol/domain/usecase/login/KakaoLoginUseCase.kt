package com.yapp.bol.domain.usecase.login

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.OAuthApiItem
import com.yapp.bol.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(token: String): Flow<ApiResult<OAuthApiItem>> = repository.getKakaoOAuth(token = token)
}
