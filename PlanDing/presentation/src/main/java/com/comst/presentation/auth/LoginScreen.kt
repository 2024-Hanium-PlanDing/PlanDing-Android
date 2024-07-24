package com.comst.presentation.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.domain.model.user.SocialLoginInformation
import com.comst.presentation.R
import com.comst.presentation.auth.LoginContract.*
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.component.PDButton
import com.comst.presentation.component.PDTextFieldOutLine
import com.comst.presentation.main.MainActivity
import com.comst.presentation.ui.theme.PlanDingTheme
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToSignUpScreen: () -> Unit
) {
    val context = LocalContext.current
    val handleEffect: (LoginBaseSideEffect) -> Unit = { effect ->
        when (effect) {
            is LoginBaseSideEffect.NavigateToMainActivity -> {
                context.startActivity(
                    MainActivity.mainIntent(context).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            }
            else -> {

            }
        }
    }

    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("Kakao", "카카오 계정 로그인 실패", error)
        } else if (token != null) {
            getKakaoUserInformation(viewModel)
        }
    }

    val onKaKaoLoginClick = { loginKakao(context, kakaoCallback) }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(top = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.app_icon),
                    contentDescription = "앱 로고",
                    modifier = Modifier
                        .size(150.dp)
                        .padding()
                )

                Spacer(Modifier.height(60.dp))

                PDTextFieldOutLine(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.id,
                    label = "아이디",
                    onValueChange = { newId -> viewModel.setIntent(LoginBaseIntent.IdChange(newId)) }
                )
                Spacer(Modifier.height(4.dp))

                PDTextFieldOutLine(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.password,
                    label = "비밀번호",
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = { newPassword -> viewModel.setIntent(LoginBaseIntent.PasswordChange(newPassword)) }
                )
                Spacer(Modifier.height(20.dp))

                PDButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "로그인하기",
                    onClick = { viewModel.setIntent(LoginBaseIntent.Login) }
                )

                Spacer(Modifier.height(20.dp))
                Text(
                    text = "SNS 계정으로 로그인하기",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                                onKaKaoLoginClick()
                            },
                        painter = painterResource(id = R.drawable.ic_login_kakao),
                        contentDescription = "카카오 로그인 버튼",
                        contentScale = ContentScale.Fit
                    )

                    Image(
                        modifier = Modifier.height(50.dp),
                        painter = painterResource(id = R.drawable.ic_login_google),
                        contentDescription = "구글 로그인 버튼",
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 24.dp)
                        .clickable(onClick = onNavigateToSignUpScreen),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "PlanDing 회원이 아니신가요?",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Sign up",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    PlanDingTheme {

    }
}

private fun getKakaoUserInformation(viewModel: LoginViewModel) {
    UserApiClient.instance.me { user, error ->
        when {
            error != null -> {
                Log.e("Kakao", "사용자 정보 실패", error)
            }

            user != null -> {
                val authId = user.id.toString()
                val email = user.kakaoAccount?.email ?: ""
                val profileUrl = user.kakaoAccount?.profile?.thumbnailImageUrl ?: ""

                val socialLoginInformation = SocialLoginInformation(
                    profileNickname = user.kakaoAccount?.profile?.nickname.toString(),
                    accountEmail = user.kakaoAccount?.email ?: "",
                    profileImage = user.kakaoAccount?.profile?.thumbnailImageUrl ?: "",
                    socialId = user.id.toString(),
                    type = SocialLoginInformation.Type.KAKAO
                )
                Log.d("카카오", "$socialLoginInformation")
                viewModel.setIntent(LoginBaseIntent.SocialLogin(socialLoginInformation))
            }
        }
    }
}

private fun loginKakao(context: Context, kakaoCallback: (OAuthToken?, Throwable?) -> Unit) {
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.e("Kakao", "카카오톡 로그인 실패", error)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }
                UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
            } else if (token != null) {
                kakaoCallback(token, null)
            }
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
    }
}