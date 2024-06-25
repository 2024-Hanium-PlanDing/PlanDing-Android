package com.comst.data.di

import android.content.Context
import com.comst.data.UserDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {

    @Provides
    @Singleton
    fun provideUserDataStore(context: Context): UserDataStore {
        return UserDataStore(context)
    }

}