package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.setting.SettingTermResponse
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.SettingTermItem

object SettingMapper {

    fun ApiResult<SettingTermResponse>.toSettingTermItem(): ApiResult<List<SettingTermItem>> =
        when (this) {
            is ApiResult.Success -> ApiResult.Success(
                data.toItem()
            )
            is ApiResult.Error -> ApiResult.Error(exception)
        }

    private fun SettingTermResponse.toItem(): List<SettingTermItem> =
        this.contents.map { content ->
            SettingTermItem(
                code = content.code,
                title = content.title,
                url = content.url,
                isRequired = content.isRequired
            )
        }
}
