package com.yapp.bol.data.repository

import com.yapp.bol.data.datasource.setting.SettingDataSource
import com.yapp.bol.data.mapper.SettingMapper.toSettingTermItem
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.SettingTermItem
import com.yapp.bol.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingDataSource: SettingDataSource,
) : SettingRepository {

    override fun getSettingTerms(): Flow<ApiResult<List<SettingTermItem>>> {
        return settingDataSource.getSettingTerms().map { it.toSettingTermItem() }
    }
}
