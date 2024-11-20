package com.example.githubrepos.features.register.signup.di

import com.example.githubrepos.features.register.signup.data.repository.SignupRepositoryImpl
import com.example.githubrepos.features.register.signup.domain.repository.ISignupRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object SignupModule {


    @Provides
    fun provideSignupRepository(signupRepositoryImpl: SignupRepositoryImpl)
            : ISignupRepository = signupRepositoryImpl

}












