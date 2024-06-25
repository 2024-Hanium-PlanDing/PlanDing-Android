package com.comst.data.di

import android.content.Context
import com.comst.data.converter.MediaImageMultipartConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageModule {
    @Provides
    @Singleton
    fun provideMediaImageMultipartConverter(context: Context): MediaImageMultipartConverter {
        return MediaImageMultipartConverter(context)
    }
}