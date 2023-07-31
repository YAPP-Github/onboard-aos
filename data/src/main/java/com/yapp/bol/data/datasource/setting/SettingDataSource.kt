package com.yapp.bol.data.datasource.setting

import com.yapp.bol.data.model.setting.SettingTermResponse
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface SettingDataSource {

    fun getSettingTerms(): Flow<ApiResult<SettingTermResponse>>
}
