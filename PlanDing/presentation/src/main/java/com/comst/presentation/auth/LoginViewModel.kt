package com.comst.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.comst.domain.model.SocialLoginInfo
import com.comst.domain.usecase.login.SetTokenUseCase
import com.comst.domain.usecase.login.SocialLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val setTokenUseCase : SetTokenUseCase,
    private val socialLoginUseCase: SocialLoginUseCase
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

    fun onUIAction(action: UIAction) {
        when (action) {
            is UIAction.IdChange -> onIdChange(action.id)
            is UIAction.PasswordChange -> onPasswordChange(action.password)
            is UIAction.Login -> onLoginClick()
            is UIAction.SocialLogin -> socialLogin(action.accountInfo)
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

    private fun socialLogin(accountInfo: SocialLoginInfo) = intent{
        val tokens = socialLoginUseCase(accountInfo).getOrThrow()
        setTokenUseCase(tokens.accessToken, tokens.refreshToken)
        postSideEffect(LoginSideEffect.NavigateToMainActivity)
    }



}

sealed class UIAction {
    data class IdChange(val id: String) : UIAction()
    data class PasswordChange(val password: String) : UIAction()
    object Login : UIAction()
    data class SocialLogin(val accountInfo: SocialLoginInfo) : UIAction()
}
@Immutable
data class LoginState(
    val id : String = "",
    val password:String = ""
)

sealed interface LoginSideEffect{
    class Toast(val message:String):LoginSideEffect
    object NavigateToMainActivity:LoginSideEffect
}