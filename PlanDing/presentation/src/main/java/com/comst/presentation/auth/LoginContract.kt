package com.comst.presentation.auth

import androidx.compose.runtime.Immutable
import com.comst.domain.model.user.SocialLoginInformation
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState

class LoginContract {

    @Immutable
    data class LoginUIState(
        val id: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
    ): UIState

    sealed class LoginSideEffect : BaseSideEffect {
        object NavigateToMainActivity : LoginSideEffect()
    }

    sealed class LoginIntent : BaseIntent {
        data class IdChange(val id: String) : LoginIntent()
        data class PasswordChange(val password: String) : LoginIntent()
        object Login : LoginIntent()
        data class SocialLogin(val accountInformation: SocialLoginInformation) : LoginIntent()
    }

    sealed class LoginEvent : BaseEvent {
        data class LoginFailure(val message: String) : LoginEvent()
    }
}