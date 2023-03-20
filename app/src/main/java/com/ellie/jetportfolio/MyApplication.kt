package com.ellie.jetportfolio

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

@HiltAndroidApp
class MyApplication : Application(), ImageLoaderFactory {

    @Inject
    lateinit var imageLoader: Provider<ImageLoader>

    init {
        if (BuildConfig.DEBUG) {
            // Timber log
            Timber.plant(Timber.DebugTree())
        }
    }
    override fun newImageLoader(): ImageLoader = imageLoader.get()
}
