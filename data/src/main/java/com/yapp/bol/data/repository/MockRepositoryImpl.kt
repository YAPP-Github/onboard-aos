package com.yapp.bol.data.repository

import com.yapp.bol.data.datasource.MockDataSource
import com.yapp.bol.data.mapper.MapperToDomain
import com.yapp.bol.domain.model.MockApiItem
import com.yapp.bol.domain.repository.MockRepository
import com.yapp.bol.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class MockRepositoryImpl @Inject constructor(
    private val mockDataSource: MockDataSource,
) : MockRepository {
    override suspend fun getKakaoMock(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): MockApiItem? {
        return MapperToDomain.mapperToMockApiItem(
            mockDataSource.getKakaoMock(remoteErrorEmitter, token = token),
        )
    }
}
