package com.comst.data.di

import com.comst.data.converter.MediaImageMultipartConverter
import com.comst.data.repository.ChatRepositoryImpl
import com.comst.data.repository.CommonScheduleRepositoryImpl
import com.comst.data.repository.GroupInviteRepositoryImpl
import com.comst.data.repository.GroupRepositoryImpl
import com.comst.data.repository.GroupScheduleRepositoryImpl
import com.comst.data.repository.GroupTaskRepositoryImpl
import com.comst.data.repository.PersonalScheduleRepositoryImpl
import com.comst.data.repository.UserRepositoryImpl
import com.comst.data.retrofit.CommonScheduleService
import com.comst.data.retrofit.GroupScheduleService
import com.comst.data.retrofit.GroupService
import com.comst.data.retrofit.PersonalScheduleService
import com.comst.data.retrofit.AuthenticatedUserService
import com.comst.data.retrofit.ChatService
import com.comst.data.retrofit.GroupInviteService
import com.comst.data.retrofit.GroupTaskService
import com.comst.data.retrofit.UnAuthenticatedUserService
import com.comst.domain.repository.ChatRepository
import com.comst.domain.repository.CommonScheduleRepository
import com.comst.domain.repository.GroupInviteRepository
import com.comst.domain.repository.GroupRepository
import com.comst.domain.repository.GroupScheduleRepository
import com.comst.domain.repository.GroupTaskRepository
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

    @Provides
    @ViewModelScoped
    fun provideGroupScheduleRepository(
        groupScheduleService: GroupScheduleService
    ): GroupScheduleRepository {
        return GroupScheduleRepositoryImpl(groupScheduleService)
    }

    @Provides
    @ViewModelScoped
    fun provideChatRepository(
        chatService: ChatService
    ): ChatRepository {
        return ChatRepositoryImpl(chatService)
    }

    @Provides
    @ViewModelScoped
    fun provideGroupInviteRepository(
        groupInviteService: GroupInviteService
    ): GroupInviteRepository {
        return GroupInviteRepositoryImpl(groupInviteService)
    }

    @Provides
    @ViewModelScoped
    fun provideGroupTaskRepository(
        groupTaskService: GroupTaskService
    ): GroupTaskRepository {
        return GroupTaskRepositoryImpl(groupTaskService)
    }
}