package com.yapp.bol.data.remote

import com.yapp.bol.data.model.file_upload.FileUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageFileApi {

    @Multipart
    @POST("v1/file")
    suspend fun postFileUpload(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("purpose") purpose: RequestBody,
    ): Response<FileUploadResponse>
}
