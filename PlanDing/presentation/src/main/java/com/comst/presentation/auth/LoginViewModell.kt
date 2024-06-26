package com.comst.presentation.auth

import androidx.lifecycle.viewModelScope
import com.comst.domain.model.user.SocialLoginInfo
import com.comst.domain.usecase.user.PostSocialLoginUseCase
import com.comst.domain.usecase.local.SetTokenUseCase
import com.comst.domain.util.onFail
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.comst.presentation.auth.LoginContract.LoginUIEvent
import com.comst.presentation.auth.LoginContract.LoginUIState
import com.comst.presentation.auth.LoginContract.LoginUISideEffect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModell @Inject constructor(
    private val setTokenUseCase: SetTokenUseCase,
    private val postSocialLoginUseCase: PostSocialLoginUseCase,
) : BaseViewModel<LoginUIState, LoginUISideEffect, LoginUIEvent>(LoginUIState()) {

    override suspend fun handleEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.IdChange -> onIdChange(event.id)
            is LoginUIEvent.PasswordChange -> onPasswordChange(event.password)
            is LoginUIEvent.Login -> onLoginClick()
            is LoginUIEvent.SocialLogin -> onSocialLogin(event.accountInfo)
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
                setEffect(LoginUISideEffect.NavigateToMainActivity)
            }
            .onFailure {
                setEffect(LoginUISideEffect.ShowToast(it.message.orEmpty()))
            }
        setState { copy(isLoading = false) }
    }

    private fun onSocialLogin(accountInfo: SocialLoginInfo) = viewModelScope.launch {
        setState { copy(isLoading = true) }
        postSocialLoginUseCase(accountInfo)
            .onSuccess {
                setTokenUseCase(it.accessToken, it.refreshToken)
                setEffect(LoginUISideEffect.NavigateToMainActivity)
            }
            .onFail {
                //setEffect(LoginUISideEffect.ShowToast(it.message.orEmpty()))
            }
        setState { copy(isLoading = false) }
    }

}