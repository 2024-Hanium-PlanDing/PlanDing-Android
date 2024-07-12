package com.comst.presentation.auth

import androidx.lifecycle.viewModelScope
import com.comst.domain.model.user.SocialLoginInfo
import com.comst.domain.usecase.user.PostSocialLoginUseCase
import com.comst.domain.usecase.local.SetTokenUseCase
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.auth.LoginContract.*
import com.comst.presentation.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val setTokenUseCase: SetTokenUseCase,
    private val postSocialLoginUseCase: PostSocialLoginUseCase,
) : BaseViewModel<LoginUIState, LoginBaseSideEffect, LoginBaseIntent, LoginBaseEvent>(LoginUIState()) {

    override fun handleIntent(intent: LoginBaseIntent) {
        when (intent) {
            is LoginBaseIntent.IdChange -> onIdChange(intent.id)
            is LoginBaseIntent.PasswordChange -> onPasswordChange(intent.password)
            is LoginBaseIntent.Login -> onLoginClick()
            is LoginBaseIntent.SocialLogin -> onSocialLogin(intent.accountInfo)
        }
    }

    override fun handleEvent(event: LoginBaseEvent) {
        when (event) {
            is LoginBaseEvent.LoginFailure -> {
                setEffect(LoginBaseSideEffect.ShowToast(event.message))
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
        val id = currentState.id
        val password = currentState.password
        setTokenUseCase(id, password)
            .onSuccess {
                setEffect(LoginBaseSideEffect.NavigateToMainActivity)
            }
            .onFailure {
                setEvent(LoginBaseEvent.LoginFailure(it.message.orEmpty()))
            }
        setState { copy(isLoading = false) }
    }

    private fun onSocialLogin(accountInfo: SocialLoginInfo) = viewModelScope.launch {
        setState { copy(isLoading = true) }
        postSocialLoginUseCase(accountInfo)
            .onSuccess {
                setTokenUseCase(it.accessToken, it.refreshToken)
                setEffect(LoginBaseSideEffect.NavigateToMainActivity)
            }
            .onFailure {

            }
        setState { copy(isLoading = false) }
    }

    override fun handleError(exception: Exception) {
        super.handleError(exception)
        setEffect(LoginBaseSideEffect.ShowToast(exception.message.orEmpty()))
    }
}