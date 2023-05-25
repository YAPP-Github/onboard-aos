package com.yapp.bol.app.di

import com.yapp.bol.data.remote.OAuthApi
import com.yapp.bol.data.utils.Utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(
        appInterceptor: com.yapp.bol.app.di.NetworkModule.AppInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(appInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideBaseUrl(): String {
        return BASE_URL
    }

    class AppInterceptor @Inject constructor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val newRequest = request().newBuilder()
                // .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build()
            proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideOAuthApiService(retrofit: Retrofit): OAuthApi {
        return retrofit.create(OAuthApi::class.java)
    }
}
