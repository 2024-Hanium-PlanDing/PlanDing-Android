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

    sealed class LoginBaseSideEffect : BaseSideEffect {
        object NavigateToMainActivity : LoginBaseSideEffect()
    }

    sealed class LoginBaseIntent : BaseIntent {
        data class IdChange(val id: String) : LoginBaseIntent()
        data class PasswordChange(val password: String) : LoginBaseIntent()
        object Login : LoginBaseIntent()
        data class SocialLogin(val accountInformation: SocialLoginInformation) : LoginBaseIntent()
    }

    sealed class LoginBaseEvent : BaseEvent {
        data class LoginFailure(val message: String) : LoginBaseEvent()
    }
}