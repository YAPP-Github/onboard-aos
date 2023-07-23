package com.yapp.bol.app.di

import com.yapp.bol.data.repository.RepositoryImpl
import com.yapp.bol.data.repository.auth.AuthRepositoryImpl
import com.yapp.bol.domain.repository.AuthRepository
import com.yapp.bol.domain.repository.Repository
import com.yapp.bol.data.repository.GroupRepositoryImpl
import com.yapp.bol.data.repository.user.UserRepositoryImpl
import com.yapp.bol.domain.repository.GroupRepository
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
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository

    @Binds
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}
