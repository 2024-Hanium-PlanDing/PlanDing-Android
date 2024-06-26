package com.comst.presentation.auth

import com.comst.domain.model.user.SocialLoginInfo
import com.comst.presentation.common.base.UIEvent
import com.comst.presentation.common.base.UISideEffect
import com.comst.presentation.common.base.UIState

class LoginContract {

    data class LoginUIState(
        val id: String = "",
        val password: String = "",
        val isLoading: Boolean = false
    ): UIState

    sealed class LoginUISideEffect : UISideEffect {
        data class ShowToast(val message: String) : LoginUISideEffect()
        object NavigateToMainActivity : LoginUISideEffect()
    }

    sealed class LoginUIEvent : UIEvent {
        data class IdChange(val id: String) : LoginUIEvent()
        data class PasswordChange(val password: String) : LoginUIEvent()
        object Login : LoginUIEvent()
        data class SocialLogin(val accountInfo: SocialLoginInfo) : LoginUIEvent()
    }
}