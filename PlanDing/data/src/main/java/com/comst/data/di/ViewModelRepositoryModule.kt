package com.comst.data.di

import android.content.Context
import com.comst.data.converter.MediaImageMultipartConverter
import com.comst.data.repository.GroupRoomRepositoryImpl
import com.comst.data.repository.UserRepositoryImpl
import com.comst.data.retrofit.GroupRoomService
import com.comst.data.retrofit.UserService
import com.comst.domain.repository.GroupRoomRepository
import com.comst.domain.repository.UserRepository
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

    @Provides
    @ViewModelScoped
    fun provideUserRepository(
        userService: UserService,
        context: Context
    ): UserRepository {
        return UserRepositoryImpl(userService, context)
    }
}