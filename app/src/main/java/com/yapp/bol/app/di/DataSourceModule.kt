package com.yapp.bol.app.di

import com.yapp.bol.data.datasource.auth.AuthDataSource
import com.yapp.bol.data.datasource.auth.AuthDataSourceImpl
import com.yapp.bol.data.datasource.file.FileDataSource
import com.yapp.bol.data.datasource.file.FileDataSourceImpl
import com.yapp.bol.data.datasource.game.GameDataSource
import com.yapp.bol.data.datasource.game.GameDataSourceImpl
import com.yapp.bol.data.datasource.group.GroupDataSource
import com.yapp.bol.data.datasource.group.GroupDataSourceImpl
import com.yapp.bol.data.datasource.match.MatchDataSource
import com.yapp.bol.data.datasource.match.MatchDataSourceImpl
import com.yapp.bol.data.datasource.member.MemberDataSource
import com.yapp.bol.data.datasource.member.MemberDataSourceImpl
import com.yapp.bol.data.datasource.setting.SettingDataSource
import com.yapp.bol.data.datasource.setting.SettingDataSourceImpl
import com.yapp.bol.data.datasource.terms.TermsDataSource
import com.yapp.bol.data.datasource.terms.TermsDataSourceImpl
import com.yapp.bol.data.datasource.user.UserDataSource
import com.yapp.bol.data.datasource.user.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindsAuthDatasource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource

    @Binds
    fun bindsGroupDatasource(groupDataSourceImpl: GroupDataSourceImpl): GroupDataSource

    @Binds
    fun bindsSettingDatasource(settingDataSource: SettingDataSourceImpl): SettingDataSource

    @Binds
    fun bindsFileDatasource(dataSource: FileDataSourceImpl): FileDataSource

    @Binds
    fun bindsGameDatasource(dataSource: GameDataSourceImpl): GameDataSource

    @Binds
    fun bindsMatchDatasource(dataSource: MatchDataSourceImpl): MatchDataSource

    @Binds
    fun bindsMemberDatasource(dataSource: MemberDataSourceImpl): MemberDataSource

    @Binds
    fun bindsTermsDatasource(dataSource: TermsDataSourceImpl): TermsDataSource

    @Binds
    fun bindsUserDatasource(dataSource: UserDataSourceImpl): UserDataSource
}
