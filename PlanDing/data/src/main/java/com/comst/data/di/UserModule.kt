package com.comst.data.di

import com.comst.data.usecase.login.SocialLoginUseCaseImpl
import com.comst.domain.usecase.SocialLoginUseCase
import com.comst.domain.usecase.user.GetUserProfileUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {
    @Binds
    abstract fun bindSocialLoginUseCase(uc: SocialLoginUseCaseImpl): SocialLoginUseCase

}