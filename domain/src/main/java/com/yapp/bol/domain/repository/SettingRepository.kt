package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.SettingTermItem
import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    fun getSettingTerms(): Flow<ApiResult<List<SettingTermItem>>>
}
