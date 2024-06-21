package com.comst.data.di

import android.content.Context
import com.comst.data.UserDataStore
import com.comst.data.converter.MediaImageMultipartConverter
import com.comst.data.repository.GroupRoomRepositoryImpl
import com.comst.data.repository.LocalRepositoryImpl
import com.comst.data.retrofit.GroupRoomService
import com.comst.domain.repository.GroupRoomRepository
import com.comst.domain.repository.LocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideUserDataStore(context: Context): UserDataStore {
        return UserDataStore(context)
    }

}