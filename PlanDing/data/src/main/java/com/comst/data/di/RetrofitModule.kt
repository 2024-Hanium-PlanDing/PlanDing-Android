package com.comst.data.di

import com.comst.data.BuildConfig.BASE_URL
import com.comst.data.retrofit.NetworkConnectionInterceptor
import com.comst.data.retrofit.TokenAuthenticator
import com.comst.data.retrofit.TokenInterceptor
import com.comst.data.util.LocalDateAdapter
import com.comst.data.util.LocalDateTimeAdapter
import com.comst.data.util.UnitJsonAdapter
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    const val NETWORK_EXCEPTION_OFFLINE_CASE = "network status is offline"
    const val NETWORK_EXCEPTION_BODY_IS_NULL = "result body is null"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UnAuthenticatedOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthenticatedOkHttpClient
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class WebSocketOkHttpClient


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UnAuthenticatedRetrofit
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthenticatedRetrofit
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class WebSocketRetrofit

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
    @Singleton
    @UnAuthenticatedOkHttpClient
    fun providesUnAuthenticatedOkHttpClient(
        networkConnectionInterceptor: NetworkConnectionInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient
            .Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(networkConnectionInterceptor)
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(false)
            .build()
    }

    @Provides
    @Singleton
    @UnAuthenticatedRetrofit
    fun providesUnAuthenticatedRetrofit(
        @AuthenticatedOkHttpClient client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("${BASE_URL}/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }



    @Provides
    @Singleton
    @AuthenticatedOkHttpClient
    fun providesAuthenticatedOkHttpClient(
        interceptor: TokenInterceptor,
        authenticator: TokenAuthenticator,
        networkConnectionInterceptor: NetworkConnectionInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient
            .Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(networkConnectionInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .authenticator(authenticator)
            .retryOnConnectionFailure(false)
            .build()
    }

    @Provides
    @Singleton
    @AuthenticatedRetrofit
    fun providesAuthenticatedRetrofit(
        @AuthenticatedOkHttpClient client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("${BASE_URL}/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    @Provides
    @Singleton
    fun moshi(): Moshi =
        Moshi.Builder()
            .add(LocalDateTimeAdapter())
            .add(LocalDateAdapter())
            .add(UnitJsonAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    @WebSocketOkHttpClient
    fun providesWebSocketOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

}