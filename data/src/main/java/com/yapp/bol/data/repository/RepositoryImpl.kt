package com.yapp.bol.data.repository

import com.yapp.bol.data.datasource.RemoteDataSource
import com.yapp.bol.data.mapper.MapperToDomain.fileUploadToDomain
import com.yapp.bol.data.mapper.MapperToDomain.newGroupToDomain
import com.yapp.bol.data.mapper.MapperToDomain.oAuthToDomain
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.NewGroupItem
import com.yapp.bol.domain.model.OAuthApiItem
import com.yapp.bol.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : Repository {
    override fun getKakaoMock(token: String): Flow<ApiResult<OAuthApiItem>> {
        return remoteDataSource.getKakaoMock(token).map {
            it.oAuthToDomain()
        }
    }

    override fun postFileUpload(token: String, file: File): Flow<ApiResult<String>> {
        return remoteDataSource.postFileUpload(token, file).map {
            it.fileUploadToDomain()
        }
    }

}
