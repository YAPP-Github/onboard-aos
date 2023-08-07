package com.yapp.bol.data.utils

import com.yapp.bol.data.BuildConfig

object Utils {
    val BASE_URL = if (BuildConfig.DEBUG) {
        BuildConfig.REMOTE_SERVER_SANDBOX
    } else {
        BuildConfig.REMOTE_SERVER
    }
}
