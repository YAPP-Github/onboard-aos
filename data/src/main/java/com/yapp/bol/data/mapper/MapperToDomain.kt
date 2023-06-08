package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.MockApiResponse
import com.yapp.bol.domain.model.LoginItem

object MapperToDomain {
    fun MockApiResponse?.toDomain(): LoginItem? = this?.toItem()

    private fun MockApiResponse.toItem(): LoginItem {
        return LoginItem(
            this.accessToken,
            this.refreshToken,
        )
    }
}
