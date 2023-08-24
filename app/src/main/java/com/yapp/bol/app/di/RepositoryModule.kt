package com.yapp.bol.app.di

import com.yapp.bol.data.repository.AuthRepositoryImpl
import com.yapp.bol.data.repository.FileRepositoryImpl
import com.yapp.bol.data.repository.GameRepositoryImpl
import com.yapp.bol.domain.repository.AuthRepository
import com.yapp.bol.data.repository.GroupRepositoryImpl
import com.yapp.bol.data.repository.MatchRepositoryImpl
import com.yapp.bol.data.repository.MemberRepositoryImpl
import com.yapp.bol.data.repository.SettingRepositoryImpl
import com.yapp.bol.data.repository.TermsRepositoryImpl
import com.yapp.bol.data.repository.UserRepositoryImpl
import com.yapp.bol.domain.repository.FileRepository
import com.yapp.bol.domain.repository.GameRepository
import com.yapp.bol.domain.repository.GroupRepository
import com.yapp.bol.domain.repository.MatchRepository
import com.yapp.bol.domain.repository.MemberRepository
import com.yapp.bol.domain.repository.SettingRepository
import com.yapp.bol.domain.repository.TermsRepository
import com.yapp.bol.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindGroupRepository(repository: GroupRepositoryImpl): GroupRepository

    @Binds
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindSettingRepository(repository: SettingRepositoryImpl): SettingRepository

    @Binds
    abstract fun bindFileRepository(repository: FileRepositoryImpl): FileRepository

    @Binds
    abstract fun bindGameRepository(repository: GameRepositoryImpl): GameRepository

    @Binds
    abstract fun bindMatchRepository(repository: MatchRepositoryImpl): MatchRepository

    @Binds
    abstract fun bindMemberRepository(repository: MemberRepositoryImpl): MemberRepository

    @Binds
    abstract fun bindTermsRepository(repository: TermsRepositoryImpl): TermsRepository

    @Binds
    abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}
