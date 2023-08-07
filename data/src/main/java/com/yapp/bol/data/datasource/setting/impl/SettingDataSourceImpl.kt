package com.yapp.bol.data.datasource.setting.impl

import com.yapp.bol.data.datasource.setting.SettingDataSource
import com.yapp.bol.data.model.setting.SettingTermResponse
import com.yapp.bol.data.remote.SettingApi
import com.yapp.bol.domain.handle.BaseRepository
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingDataSourceImpl @Inject constructor(
    private val settingApi: SettingApi,
) : BaseRepository(), SettingDataSource {

    override fun getSettingTerms(): Flow<ApiResult<SettingTermResponse>> {
        return flow {
            safeApiCall {
                settingApi.getSettingTerms()
            }.also { emit(it) }
        }
    }
}
