package com.yapp.bol.data.repository

import com.yapp.bol.data.datasource.file.FileDataSource
import com.yapp.bol.data.mapper.FileMapper.fileUploadToDomain
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.repository.FileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileDataSource: FileDataSource,
) : FileRepository {

    override fun postFileUpload(file: File): Flow<ApiResult<String>> {
        return fileDataSource.postFileUpload(file).map {
            it.fileUploadToDomain()
        }
    }
}
