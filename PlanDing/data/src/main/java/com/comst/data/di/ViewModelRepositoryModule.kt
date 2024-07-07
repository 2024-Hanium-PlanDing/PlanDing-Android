package com.comst.data.di

import com.comst.data.converter.MediaImageMultipartConverter
import com.comst.data.repository.CommonScheduleRepositoryImpl
import com.comst.data.repository.GroupRepositoryImpl
import com.comst.data.repository.PersonalScheduleRepositoryImpl
import com.comst.data.repository.UserRepositoryImpl
import com.comst.data.retrofit.CommonScheduleService
import com.comst.data.retrofit.GroupService
import com.comst.data.retrofit.PersonalScheduleService
import com.comst.data.retrofit.UserService
import com.comst.domain.repository.CommonScheduleRepository
import com.comst.domain.repository.GroupRepository
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
    fun provideGroupRepository(
        groupService: GroupService,
        mediaImageMultipartConverter: MediaImageMultipartConverter,
    ): GroupRepository {
        return GroupRepositoryImpl(groupService, mediaImageMultipartConverter)
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