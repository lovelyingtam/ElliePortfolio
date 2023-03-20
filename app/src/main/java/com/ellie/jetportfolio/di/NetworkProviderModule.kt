package com.ellie.jetportfolio.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import com.ellie.jetportfolio.BuildConfig
import com.ellie.jetportfolio.data.api.ApiConstants
import com.ellie.jetportfolio.data.api.ApiIndex
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkProviderModule {
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

    /**
     * Since we're displaying SVGs in the app, Coil needs an ImageLoader which supports this
     * format. During Coil's initialization it will call `applicationContext.newImageLoader()` to
     * obtain an ImageLoader.
     *
     * @see <a href="https://github.com/coil-kt/coil/blob/main/coil-singleton/src/main/java/coil/Coil.kt">Coil</a>
     */

    @Provides
    @Singleton
    fun provideOkHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                },
        )
        .build()

    @Provides
    @Singleton
    fun provideImageLoader(
        okHttpCallFactory: Call.Factory,
        @ApplicationContext application: Context,
    ): ImageLoader = ImageLoader.Builder(application)
        .callFactory(okHttpCallFactory)
        // Assume most content images are versioned urls
        // but some problematic images are fetching each time
        .respectCacheHeaders(false)
        .crossfade(true)
        .memoryCache {
            MemoryCache.Builder(application)
                .maxSizePercent(0.25)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(application.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        }
        .apply {
            if (BuildConfig.DEBUG) {
                logger(DebugLogger())
            }
        }
        .build()
}