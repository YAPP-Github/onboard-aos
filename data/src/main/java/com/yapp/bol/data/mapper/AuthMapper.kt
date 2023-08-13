package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.login.LoginResponse
import com.yapp.bol.domain.model.LoginItem

object AuthMapper {

    fun LoginResponse?.toDomain(): LoginItem? = this?.toItem()

    private fun LoginResponse.toItem(): LoginItem {
        return LoginItem(
            this.accessToken,
            this.refreshToken,
        )
    }
}
