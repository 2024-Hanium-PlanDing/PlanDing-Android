package com.comst.presentation.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
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
import com.comst.domain.model.SocialLoginInfo
import com.comst.presentation.R
import com.comst.presentation.component.PDButton
import com.comst.presentation.component.PDTextFiledOutLine
import com.comst.presentation.main.MainActivity
import com.comst.presentation.ui.theme.PlanDingTheme
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToSignUpScreen: () -> Unit
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is LoginSideEffect.Toast -> Toast.makeText(
                context,
                sideEffect.message,
                Toast.LENGTH_SHORT
            ).show()

            LoginSideEffect.NavigateToMainActivity -> {
                context.startActivity(
                    Intent(
                        context, MainActivity::class.java
                    ).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            }
        }
    }

    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when {
            error != null -> {
                Log.e("Kakao", "카카오 계정 로그인 실패", error)
            }

            token != null -> {
                getKakaoUserInfo(viewModel)
            }
        }
    }

    val onKaKaoLoginClick = { loginKakao(context, kakaoCallback) }


    LoginScreen(
        id = state.id,
        password = state.password,
        onIdChange = viewModel::onIdChange,
        onPasswordChange = viewModel::onPasswordChange,
        onNavigateToSignUpScreen = onNavigateToSignUpScreen,
        onLoginClick = viewModel::onLoginClick,
        onKaKaoLoginClick = onKaKaoLoginClick
    )
}

@Composable
private fun LoginScreen(
    id: String,
    password: String,
    onIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNavigateToSignUpScreen: () -> Unit,
    onLoginClick: () -> Unit,
    onKaKaoLoginClick: () -> Unit,
) {
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

            PDTextFiledOutLine(
                modifier = Modifier.fillMaxWidth(),
                value = id,
                label = "아이디",
                onValueChange = onIdChange
            )
            Spacer(Modifier.height(4.dp))

            PDTextFiledOutLine(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                label = "비밀번호",
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = onPasswordChange
            )
            Spacer(Modifier.height(20.dp))

            PDButton(
                modifier = Modifier.fillMaxWidth(),
                text = "로그인하기",
                onClick = onLoginClick
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
                    painter = painterResource(id = R.drawable.login_kakao),
                    contentDescription = "카카오 로그인 버튼",
                    contentScale = ContentScale.Fit
                )

                Image(
                    modifier = Modifier.height(50.dp),
                    painter = painterResource(id = R.drawable.login_google),
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


@Preview
@Composable
private fun LoginScreenPreview() {
    PlanDingTheme {
        LoginScreen(
            id = "",
            password = "",
            onIdChange = {},
            onPasswordChange = {},
            onNavigateToSignUpScreen = {},
            onLoginClick = {},
            onKaKaoLoginClick = {}
        )
    }
}

private fun getKakaoUserInfo(viewModel: LoginViewModel) {
    UserApiClient.instance.me { user, error ->
        when {
            error != null -> {
                Log.e("Kakao", "사용저 정보 실패", error)
            }

            user != null -> {
                viewModel.socialLogin(
                    SocialLoginInfo(
                        profileNickname = user.kakaoAccount?.name.toString(),
                        accountEmail = user.kakaoAccount?.email ?: "",
                        profileImage = user.kakaoAccount?.profile?.thumbnailImageUrl ?: "",
                        socialId = user.id.toString(),
                        type = SocialLoginInfo.Type.KAKAO

                    )
                )
            }
        }
    }
}

private fun loginKakao(context: Context, kakaoCallback: (OAuthToken?, Throwable?) -> Unit) {
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.e("Kakao", "카카오톡 로그인 실패", error)
            }
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                return@loginWithKakaoTalk
            }

            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
    }

}