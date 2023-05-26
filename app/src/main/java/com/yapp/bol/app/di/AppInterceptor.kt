package com.yapp.bol.app.di

import okhttp3.Interceptor
import okhttp3.Response

class AppInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .build()
        proceed(newRequest)
    }
}
