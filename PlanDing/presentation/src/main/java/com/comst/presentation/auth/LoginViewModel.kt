package com.comst.presentation.auth

import androidx.lifecycle.viewModelScope
import com.comst.domain.model.user.SocialLoginInformation
import com.comst.domain.usecase.user.PostSocialLoginUseCase
import com.comst.domain.usecase.local.SetUserDataUseCase
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.auth.LoginContract.*
import com.comst.presentation.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val setUserDataUseCase: SetUserDataUseCase,
    private val postSocialLoginUseCase: PostSocialLoginUseCase,
) : BaseViewModel<LoginUIState, LoginSideEffect, LoginIntent, LoginEvent>(LoginUIState()) {

    override fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.IdChange -> onIdChange(intent.id)
            is LoginIntent.PasswordChange -> onPasswordChange(intent.password)
            is LoginIntent.Login -> onLoginClick()
            is LoginIntent.SocialLogin -> onSocialLogin(intent.accountInformation)
        }
    }

    override fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.LoginFailure -> {
                setToastEffect(event.message)
            }
        }
    }

    private fun onIdChange(id: String) {
        setState {
            copy(id = id)
        }
    }

    private fun onPasswordChange(password: String) {
        setState {
            copy(password = password)
        }
    }

    private fun onLoginClick() = viewModelScope.launch {
        setState { copy(isLoading = true) }

        setState { copy(isLoading = false) }
    }

    private fun onSocialLogin(accountInformation: SocialLoginInformation) = viewModelScope.launch {
        setState { copy(isLoading = true) }
        postSocialLoginUseCase(accountInformation)
            .onSuccess {
                setUserDataUseCase(it.accessToken, it.refreshToken, it.userCode)
                setEffect(LoginSideEffect.NavigateToMainActivity)
            }
            .onFailure {

            }
        setState { copy(isLoading = false) }
    }

    override fun handleError(exception: Exception) {
        super.handleError(exception)
        setToastEffect(exception.message.orEmpty())
    }
}