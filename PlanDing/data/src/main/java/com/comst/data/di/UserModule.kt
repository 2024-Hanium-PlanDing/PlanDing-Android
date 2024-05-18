package com.comst.data.di

import com.comst.data.usecase.login.ClearTokenUseCaseImpl
import com.comst.data.usecase.login.GetTokenUseCaseImpl
import com.comst.data.usecase.login.SetTokenUseCaseImpl
import com.comst.data.usecase.login.SocialLoginUseCaseImpl
import com.comst.data.usecase.user.GetUserProfileUseCaseImpl
import com.comst.domain.usecase.login.ClearTokenUseCase
import com.comst.domain.usecase.login.GetTokenUseCase
import com.comst.domain.usecase.login.SetTokenUseCase
import com.comst.domain.usecase.login.SocialLoginUseCase
import com.comst.domain.usecase.user.GetUserProfileUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Binds
    abstract fun bindGetTokenUseCase(uc :GetTokenUseCaseImpl):GetTokenUseCase

    @Binds
    abstract fun bindSetTokenUseCase(uc :SetTokenUseCaseImpl):SetTokenUseCase

    @Binds
    abstract fun bindClearTokenUseCase(uc :ClearTokenUseCaseImpl):ClearTokenUseCase

    @Binds
    abstract fun bindSocialLoginUseCase(uc: SocialLoginUseCaseImpl):SocialLoginUseCase

    @Binds
    abstract fun bindGetUserProfileUseCase(uc: GetUserProfileUseCaseImpl):GetUserProfileUseCase
}