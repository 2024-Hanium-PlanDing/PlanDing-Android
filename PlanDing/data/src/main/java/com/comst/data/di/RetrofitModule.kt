package com.comst.data.di

import com.comst.data.BuildConfig.BASE_URL
import com.comst.data.retrofit.NetworkConnectionInterceptor
import com.comst.data.retrofit.TokenAuthenticator
import com.comst.data.retrofit.TokenInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    const val NETWORK_EXCEPTION_OFFLINE_CASE = "network status is offline"
    const val NETWORK_EXCEPTION_BODY_IS_NULL = "result body is null"

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
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("${BASE_URL}/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

}