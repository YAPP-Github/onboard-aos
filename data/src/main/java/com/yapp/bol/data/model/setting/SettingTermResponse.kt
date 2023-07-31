package com.yapp.bol.data.model.setting

data class SettingTermResponse(
    val contents: List<SettingTermDTO>
)

data class SettingTermDTO(
    val code: String,
    val title: String,
    val url: String,
    val isRequired: Boolean,
)
