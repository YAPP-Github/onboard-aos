package com.yapp.bol.app.di

import com.yapp.bol.data.datasource.impl.RemoteDataSource
import com.yapp.bol.data.datasource.auth.AuthDataSource
import com.yapp.bol.data.datasource.auth.AuthDataSourceImpl
import com.yapp.bol.data.datasource.group.GroupDataSource
import com.yapp.bol.data.datasource.group.impl.GroupDataSourceImpl
import com.yapp.bol.data.datasource.impl.RemoteDataSourceImpl
import com.yapp.bol.data.datasource.rank.RankDataSource
import com.yapp.bol.data.datasource.rank.impl.RankDataSourceImpl
import com.yapp.bol.data.datasource.setting.SettingDataSource
import com.yapp.bol.data.datasource.setting.impl.SettingDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindsRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    fun bindsAuthDatasource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource

    @Binds
    fun bindsGroupDatasource(groupDataSourceImpl: GroupDataSourceImpl): GroupDataSource

    @Binds
    fun bindsRankDatasource(rankDataSource: RankDataSourceImpl): RankDataSource

    @Binds
    fun bindsSettingDatasource(settingDataSource: SettingDataSourceImpl): SettingDataSource
}
