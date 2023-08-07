package com.yapp.bol.data.remote

import com.yapp.bol.data.model.setting.SettingTermResponse
import retrofit2.Response
import retrofit2.http.GET

interface SettingApi {

    @GET("/v1/setting/terms")
    suspend fun getSettingTerms(): Response<SettingTermResponse>
}
