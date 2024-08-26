package com.comst.data.di

import com.comst.data.retrofit.CommonScheduleService
import com.comst.data.retrofit.GroupScheduleService
import com.comst.data.retrofit.GroupService
import com.comst.data.retrofit.PersonalScheduleService
import com.comst.data.retrofit.AuthenticatedUserService
import com.comst.data.retrofit.ChatService
import com.comst.data.retrofit.GroupInviteService
import com.comst.data.retrofit.GroupTaskService
import com.comst.data.retrofit.UnAuthenticatedUserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideUnAuthenticatedUserService(@RetrofitModule.UnAuthenticatedRetrofit retrofit: Retrofit): UnAuthenticatedUserService {
        return retrofit.create(UnAuthenticatedUserService::class.java)
    }
    @Provides
    fun provideAuthenticatedUserService(@RetrofitModule.AuthenticatedRetrofit retrofit: Retrofit): AuthenticatedUserService {
        return retrofit.create(AuthenticatedUserService::class.java)
    }

    @Provides
    fun provideGroupService(@RetrofitModule.AuthenticatedRetrofit retrofit: Retrofit): GroupService {
        return retrofit.create(GroupService::class.java)
    }

    @Provides
    fun provideCommonScheduleService(@RetrofitModule.AuthenticatedRetrofit retrofit: Retrofit): CommonScheduleService {
        return retrofit.create(CommonScheduleService::class.java)
    }

    @Provides
    fun providePersonalScheduleService(@RetrofitModule.AuthenticatedRetrofit retrofit: Retrofit): PersonalScheduleService {
        return retrofit.create(PersonalScheduleService::class.java)
    }

    @Provides
    fun provideGroupScheduleService(@RetrofitModule.AuthenticatedRetrofit retrofit: Retrofit): GroupScheduleService {
        return retrofit.create(GroupScheduleService::class.java)
    }

    @Provides
    fun provideChatService(@RetrofitModule.AuthenticatedRetrofit retrofit: Retrofit): ChatService {
        return retrofit.create(ChatService::class.java)
    }

    @Provides
    fun provideGroupInviteService(@RetrofitModule.AuthenticatedRetrofit retrofit: Retrofit): GroupInviteService {
        return retrofit.create(GroupInviteService::class.java)
    }

    @Provides
    fun provideGroupTaskService(@RetrofitModule.AuthenticatedRetrofit retrofit: Retrofit): GroupTaskService {
        return retrofit.create(GroupTaskService::class.java)
    }

}