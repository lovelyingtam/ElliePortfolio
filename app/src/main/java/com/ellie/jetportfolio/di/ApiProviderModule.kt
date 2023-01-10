package com.ellie.jetportfolio.di

import com.ellie.jetportfolio.data.api.ApiConstants
import com.ellie.jetportfolio.data.api.ApiIndex
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiProviderModule {
    @Provides
    @Singleton
    fun provideApi(retrofitBuilder: Retrofit.Builder): ApiIndex {
        return retrofitBuilder.build().create(ApiIndex::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
    }
}