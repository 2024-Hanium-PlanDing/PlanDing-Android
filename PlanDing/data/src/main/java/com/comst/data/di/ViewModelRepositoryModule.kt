package com.comst.data.di

import com.comst.data.converter.MediaImageMultipartConverter
import com.comst.data.repository.CommonScheduleRepositoryImpl
import com.comst.data.repository.GroupRoomRepositoryImpl
import com.comst.data.repository.PersonalScheduleRepositoryImpl
import com.comst.data.repository.UserRepositoryImpl
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
    ): GroupRoomRepository {
        return GroupRoomRepositoryImpl(groupRoomService, mediaImageMultipartConverter)
    }

    @Provides
    @ViewModelScoped
    fun provideUserRepository(
        userService: UserService,
    ): UserRepository {
        return UserRepositoryImpl(userService)
    }

    @Provides
    @ViewModelScoped
    fun provideCommonScheduleRepositoryRepository(
        commonScheduleService: CommonScheduleService,
    ): CommonScheduleRepository {
        return CommonScheduleRepositoryImpl(commonScheduleService)
    }

    @Provides
    @ViewModelScoped
    fun providePersonalScheduleRepository(
        personalScheduleService: PersonalScheduleService,
    ): PersonalScheduleRepository {
        return PersonalScheduleRepositoryImpl(personalScheduleService)
    }
}