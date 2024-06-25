package com.comst.presentation.auth

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.comst.domain.model.user.SocialLoginInfo
import com.comst.domain.usecase.user.PostSocialLoginUseCase
import com.comst.domain.usecase.local.SetTokenUseCase
import com.comst.domain.util.onFail
import com.comst.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val setTokenUseCase : SetTokenUseCase,
    private val postSocialLoginUseCase: PostSocialLoginUseCase
) : ViewModel(), ContainerHost<LoginState,LoginSideEffect> {

    override val container: Container<LoginState, LoginSideEffect> = container(
        initialState = LoginState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(LoginSideEffect.Toast(message = throwable.message.orEmpty()))
                }
            }
        }
    )

    fun onUIAction(action: LoginUIAction) {
        when (action) {
            is LoginUIAction.IdChange -> onIdChange(action.id)
            is LoginUIAction.PasswordChange -> onPasswordChange(action.password)
            is LoginUIAction.Login -> onLoginClick()
            is LoginUIAction.SocialLogin -> onSocialLogin(action.accountInfo)
        }
    }

    private fun onLoginClick() = intent{
        val id = state.id
        val password = state.password
        //postSideEffect(LoginSideEffect.NavigateToMainActivity)
        setTokenUseCase("aaa","aaa")
    }

    private fun onIdChange(id: String) = blockingIntent{
        reduce {
            state.copy(id = id)
        }
    }

    private fun onPasswordChange(password: String) = blockingIntent{
        reduce {
            state.copy(password = password)
        }
    }

    private fun onSocialLogin(accountInfo: SocialLoginInfo) = intent{
        postSocialLoginUseCase(accountInfo).onSuccess {
            setTokenUseCase(it.accessToken, it.refreshToken)
            postSideEffect(LoginSideEffect.NavigateToMainActivity)
        }.onFail {

        }
    }



}

sealed class LoginUIAction {
    data class IdChange(val id: String) : LoginUIAction()
    data class PasswordChange(val password: String) : LoginUIAction()
    object Login : LoginUIAction()
    data class SocialLogin(val accountInfo: SocialLoginInfo) : LoginUIAction()
}
@Immutable
data class LoginState(
    val id : String = "",
    val password:String = ""
)

sealed interface LoginSideEffect{
    data class Toast(val message:String):LoginSideEffect
    object NavigateToMainActivity:LoginSideEffect
}