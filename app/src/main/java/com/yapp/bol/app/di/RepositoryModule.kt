package com.yapp.bol.app.di

import com.yapp.bol.data.repository.RepositoryImpl
import com.yapp.bol.data.repository.auth.AuthRepositoryImpl
import com.yapp.bol.domain.repository.AuthRepository
import com.yapp.bol.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMockRepository(repositoryImpl: RepositoryImpl): Repository

    @Binds
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository
}
