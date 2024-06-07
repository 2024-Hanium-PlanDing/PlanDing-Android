package com.comst.data.di

import com.comst.data.usecase.personalSchedule.PostPersonalScheduleUseCaseImpl
import com.comst.domain.usecase.personalSchedule.PostPersonalScheduleUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface PersonalScheduleModule {

    @Binds
    abstract fun bindPostPersonalScheduleUseCase(uc:PostPersonalScheduleUseCaseImpl) : PostPersonalScheduleUseCase
}