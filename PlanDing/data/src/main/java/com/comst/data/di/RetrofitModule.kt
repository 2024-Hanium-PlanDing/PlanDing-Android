package com.comst.data.di

import com.comst.data.BuildConfig.BASE_URL
import com.comst.data.retrofit.CommonScheduleService
import com.comst.data.retrofit.GroupRoomService
import com.comst.data.retrofit.PersonalScheduleService
import com.comst.data.retrofit.TokenAuthenticator
import com.comst.data.retrofit.TokenInterceptor
import com.comst.data.retrofit.UserService
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun providesConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .setLenient()
                .create()
        )
    }

    @Provides
    fun providesOkHttpClient(
        interceptor: TokenInterceptor,
        authenticator: TokenAuthenticator,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .authenticator(authenticator)
            .build()
    }

    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("${BASE_URL}/api/")
            .addConverterFactory(gsonConverterFactory)
            .client(client)
            .build()
    }

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    fun provideGroupRoomService(retrofit: Retrofit): GroupRoomService{
        return retrofit.create(GroupRoomService::class.java)
    }

    @Provides
    fun provideCommonScheduleService(retrofit: Retrofit): CommonScheduleService{
        return retrofit.create(CommonScheduleService::class.java)
    }

    @Provides
    fun providePersonalScheduleService(retrofit: Retrofit): PersonalScheduleService{
        return retrofit.create(PersonalScheduleService::class.java)
    }
}