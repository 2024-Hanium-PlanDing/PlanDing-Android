package com.comst.data.di

import com.comst.data.retrofit.CommonScheduleService
import com.comst.data.retrofit.GroupService
import com.comst.data.retrofit.PersonalScheduleService
import com.comst.data.retrofit.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    fun provideGroupService(retrofit: Retrofit): GroupService {
        return retrofit.create(GroupService::class.java)
    }

    @Provides
    fun provideCommonScheduleService(retrofit: Retrofit): CommonScheduleService {
        return retrofit.create(CommonScheduleService::class.java)
    }

    @Provides
    fun providePersonalScheduleService(retrofit: Retrofit): PersonalScheduleService {
        return retrofit.create(PersonalScheduleService::class.java)
    }
}