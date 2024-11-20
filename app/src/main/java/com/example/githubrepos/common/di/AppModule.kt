package com.example.githubrepos.common.di

import com.example.githubrepos.common.data.repository.FacebookAuthImpl
import com.example.githubrepos.common.data.repository.GoogleAuthImpl
import com.example.githubrepos.common.domain.repository.IFacebookAuth
import com.example.githubrepos.common.domain.repository.IGoogleAuth
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


    @Provides
    fun provideExtraAuthOptions(
        extraAuthOptionsImpl: GoogleAuthImpl
    ): IGoogleAuth {
        return extraAuthOptionsImpl
    }

    @Provides
    fun provideFacebookAuth(
        facebookAuthImpl: FacebookAuthImpl
    ): IFacebookAuth {
        return facebookAuthImpl
    }


}







