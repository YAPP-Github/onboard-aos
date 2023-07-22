package com.yapp.bol.domain.handle

import com.google.gson.Gson
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.ErrorItem
import com.yapp.bol.domain.model.ErrorItem.Companion.NETWORK_ERROR_CODE
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {
    suspend fun <T : Any> safeApiCall(callFunction: suspend () -> Response<T>): ApiResult<T> {
        val response: Response<T>

        try {
            response = callFunction()
        } catch (e: Exception) {
            return when (e) {
                is IOException -> {
                    ApiResult.Error(
                        ErrorItem(code = NETWORK_ERROR_CODE.toString(), message = e.message ?: "Internet error runs"),
                    )
                }

                else -> ApiResult.Error(ErrorItem(message = e.message.toString()))
            }
        }

        val body = response.body()

        return if (body != null) {
            ApiResult.Success(body)
        } else {
            val json = response.errorBody()?.string() ?: ""
            ApiResult.Error(
                Gson().fromJson(json, ErrorItem::class.java),
            )
        }
    }
}
