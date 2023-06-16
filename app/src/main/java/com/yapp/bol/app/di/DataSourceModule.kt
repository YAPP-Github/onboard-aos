package com.yapp.bol.app.di

import com.yapp.bol.data.datasource.auth.AuthDataSource
import com.yapp.bol.data.datasource.auth.impl.AuthDataSourceImpl
import com.yapp.bol.data.datasource.auth.impl.datastore.AuthManager
import com.yapp.bol.data.datasource.mock.MockDataSource
import com.yapp.bol.data.datasource.mock.impl.MockDataSourceImpl
import com.yapp.bol.data.remote.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideMockDataSource(
        oauthApi: LoginApi,
    ): MockDataSource {
        return MockDataSourceImpl(oauthApi)
    }

    @Provides
    @Singleton
    fun provideAuthDatasource(
        dataStore: AuthManager,
    ): AuthDataSource {
        return AuthDataSourceImpl(dataStore)
    }
}
