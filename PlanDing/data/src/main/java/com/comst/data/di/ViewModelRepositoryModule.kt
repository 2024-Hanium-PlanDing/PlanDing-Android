package com.comst.data.di

import android.content.Context
import com.comst.data.converter.MediaImageMultipartConverter
import com.comst.data.repository.CommonScheduleRepositoryImpl
import com.comst.data.repository.GroupRoomRepositoryImpl
import com.comst.data.repository.PersonalScheduleRepositoryImpl
import com.comst.data.repository.UserRepositoryImpl
import com.comst.data.retrofit.ApiHandler
import com.comst.data.retrofit.CommonScheduleService
import com.comst.data.retrofit.GroupRoomService
import com.comst.data.retrofit.PersonalScheduleService
import com.comst.data.retrofit.UserService
import com.comst.domain.repository.CommonScheduleRepository
import com.comst.domain.repository.GroupRoomRepository
import com.comst.domain.repository.PersonalScheduleRepository
import com.comst.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelRepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideGroupRoomRepository(
        groupRoomService: GroupRoomService,
        mediaImageMultipartConverter: MediaImageMultipartConverter,
        apiHandler: ApiHandler
    ): GroupRoomRepository {
        return GroupRoomRepositoryImpl(groupRoomService, mediaImageMultipartConverter,apiHandler)
    }

    @Provides
    @ViewModelScoped
    fun provideUserRepository(
        userService: UserService,
        apiHandler: ApiHandler
    ): UserRepository {
        return UserRepositoryImpl(userService, apiHandler)
    }

    @Provides
    @ViewModelScoped
    fun provideCommonScheduleRepositoryRepository(
        commonScheduleService: CommonScheduleService,
        apiHandler: ApiHandler
    ): CommonScheduleRepository {
        return CommonScheduleRepositoryImpl(commonScheduleService, apiHandler)
    }

    @Provides
    @ViewModelScoped
    fun providePersonalScheduleRepository(
        personalScheduleService: PersonalScheduleService,
        apiHandler: ApiHandler
    ): PersonalScheduleRepository {
        return PersonalScheduleRepositoryImpl(personalScheduleService, apiHandler)
    }
}