package com.comst.data.di

import com.comst.data.usecase.commonSchedule.GetCommonScheduleTodayListUseCaseImpl
import com.comst.data.usecase.commonSchedule.GetCommonScheduleWeekListUseCaseImpl
import com.comst.domain.usecase.commonSchedule.GetCommonScheduleTodayListUseCase
import com.comst.domain.usecase.commonSchedule.GetCommonScheduleWeekListUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class CommonScheduleModule {

    @Binds
    abstract fun bindGetCommonScheduleTodayListUseCase(uc:GetCommonScheduleTodayListUseCaseImpl):GetCommonScheduleTodayListUseCase

    @Binds
    abstract fun bindGetCommonScheduleWeekListUseCase(uc:GetCommonScheduleWeekListUseCaseImpl):GetCommonScheduleWeekListUseCase
}