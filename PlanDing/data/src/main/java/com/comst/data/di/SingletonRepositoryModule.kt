package com.comst.data.di

import android.content.Context
import com.comst.data.UserDataStore
import com.comst.data.repository.FileRepositoryImpl
import com.comst.data.repository.LocalRepositoryImpl
import com.comst.data.repository.UserRepositoryImpl
import com.comst.data.retrofit.AuthenticatedUserService
import com.comst.data.retrofit.UnAuthenticatedUserService
import com.comst.domain.repository.FileRepository
import com.comst.domain.repository.LocalRepository
import com.comst.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonRepositoryModule {

    @Provides
    @Singleton
    fun provideLocalRepository(userDataStore: UserDataStore): LocalRepository {
        return LocalRepositoryImpl(userDataStore)
    }

    @Provides
    @Singleton
    fun provideFileRepository(context: Context): FileRepository {
        return FileRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        unAuthenticatedUserService: UnAuthenticatedUserService,
        authenticatedUserService: AuthenticatedUserService,
    ): UserRepository {
        return UserRepositoryImpl(
            unAuthenticatedUserService,
            authenticatedUserService
        )
    }

}