package com.yapp.bol.data.remote

import com.yapp.bol.data.model.group.response.ImageFileUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileApi {

    @Multipart
    @POST("v1/file")
    suspend fun postFileUpload(
        @Part file: MultipartBody.Part,
        @Part("purpose") purpose: RequestBody,
    ): Response<ImageFileUploadResponse>
}
