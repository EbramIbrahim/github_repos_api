package com.example.githubrepos.features.register.login.di

import com.example.githubrepos.features.register.login.data.repository.LoginRepositoryImpl
import com.example.githubrepos.features.register.login.domain.repository.ILoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object LoginModule {


    @Provides
    fun provideSLoginRepository(loginRepositoryImpl: LoginRepositoryImpl)
            : ILoginRepository = loginRepositoryImpl

}












