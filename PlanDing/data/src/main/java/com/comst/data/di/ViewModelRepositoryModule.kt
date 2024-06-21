package com.comst.data.di

import android.content.Context
import com.comst.data.converter.MediaImageMultipartConverter
import com.comst.data.repository.GroupRoomRepositoryImpl
import com.comst.data.retrofit.GroupRoomService
import com.comst.domain.repository.GroupRoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelRepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideGroupRoomRepository(
        groupRoomService: GroupRoomService,
        mediaImageMultipartConverter: MediaImageMultipartConverter,
        context: Context
    ): GroupRoomRepository {
        return GroupRoomRepositoryImpl(groupRoomService, mediaImageMultipartConverter, context)
    }
}