package com.comst.data.di

import android.app.Application
import android.content.Context
import com.comst.data.converter.MediaImageMultipartConverter
import com.comst.data.repository.FileRepositoryImpl
import com.comst.domain.repository.FileRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

}