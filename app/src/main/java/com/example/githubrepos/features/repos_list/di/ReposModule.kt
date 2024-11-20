package com.example.githubrepos.features.repos_list.di

import com.example.githubrepos.common.utils.Constant.GIT_HUB_BASE_URL
import com.example.githubrepos.features.repos_list.data.repository.GetUserReposImpl
import com.example.githubrepos.features.repos_list.data.repository.remote.ReposApi
import com.example.githubrepos.features.repos_list.domain.repository.IGetUserRepos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ReposModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient().newBuilder().apply {
            connectTimeout(30L, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            connectionPool(
                ConnectionPool(30L.toInt(), 500000, TimeUnit.MILLISECONDS)
            )
            readTimeout(30L, TimeUnit.SECONDS)
            writeTimeout(30L, TimeUnit.SECONDS)
        }
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient.Builder,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient.build())
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(GIT_HUB_BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideAlReposApiService(retrofit: Retrofit): ReposApi =
        retrofit.create(ReposApi::class.java)

    @Provides
    fun provideGetUserRepos(
        getUserReposImpl: GetUserReposImpl
    ): IGetUserRepos = getUserReposImpl


}










