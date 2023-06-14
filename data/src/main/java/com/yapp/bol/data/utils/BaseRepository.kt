package com.yapp.bol.data.utils

import com.yapp.bol.domain.model.ApiResult
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
                    ApiResult.Error(IOException(e.message ?: "Internet error runs"))
                }

                else -> ApiResult.Error(e)
            }
        }

        val body = response.body()
        return if (body != null) {
            ApiResult.Success(body)
        } else {
            ApiResult.Error(IOException("Body Null Error"))
        }
    }
}
