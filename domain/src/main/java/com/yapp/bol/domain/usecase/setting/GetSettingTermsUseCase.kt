package com.yapp.bol.domain.usecase.setting

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.SettingTermItem
import com.yapp.bol.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingTermsUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {

    operator fun invoke(): Flow<ApiResult<List<SettingTermItem>>> =
        settingRepository.getSettingTerms()
}
