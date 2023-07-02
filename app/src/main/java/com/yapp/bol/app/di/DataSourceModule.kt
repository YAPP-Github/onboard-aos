package com.yapp.bol.app.di

import com.yapp.bol.data.datasource.RemoteDataSource
import com.yapp.bol.data.datasource.auth.AuthDataSource
import com.yapp.bol.data.datasource.auth.impl.AuthDataSourceImpl
import com.yapp.bol.data.datasource.group.GroupDataSource
import com.yapp.bol.data.datasource.group.impl.GroupDataSourceImpl
import com.yapp.bol.data.datasource.impl.RemoteDataSourceImpl
import com.yapp.bol.data.datasource.rank.RankDataSource
import com.yapp.bol.data.datasource.rank.impl.RankDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindsRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    @Singleton
    fun bindsAuthDatasource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource

    @Binds
    @Singleton
    fun bindsGroupDatasource(groupDataSourceImpl: GroupDataSourceImpl): GroupDataSource

    @Binds
    @Singleton
    fun bindsRankDatasource(rankDataSource: RankDataSourceImpl): RankDataSource
}
