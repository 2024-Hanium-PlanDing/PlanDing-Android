package com.comst.data.di

import com.comst.data.usecase.groupRoom.PostGroupRoomUseCaseImpl
import com.comst.domain.usecase.groupRoom.PostGroupRoomUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class GroupRoomModule {

    @Binds
    abstract fun bindPostGroupRoomUseCase(uc:PostGroupRoomUseCaseImpl) : PostGroupRoomUseCase
}