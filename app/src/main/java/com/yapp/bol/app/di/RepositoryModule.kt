package com.yapp.bol.app.di

import com.yapp.bol.data.repository.RepositoryImpl
import com.yapp.bol.data.repository.auth.AuthRepositoryImpl
import com.yapp.bol.domain.repository.AuthRepository
import com.yapp.bol.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindRepository(repositoryImpl: RepositoryImpl): Repository

    @Binds
    fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository
}
