package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.MockApiResponse
import com.yapp.bol.domain.model.MockApiItem

object MapperToDomain {
    // data -> domain
    fun mapperToMockApiItem(response: MockApiResponse?): MockApiItem? {
        return response?.toItem()
    }

    private fun MockApiResponse.toItem(): MockApiItem {
        return MockApiItem(
            this.accessToken,
            this.refreshToken
        )
    }
}
