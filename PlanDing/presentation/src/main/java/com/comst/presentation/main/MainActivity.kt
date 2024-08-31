package com.comst.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.comst.domain.usecase.user.PostFCMTokenUseCase
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.ui.theme.PlanDingTheme
import com.comst.presentation.util.PDFirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var postFCMTokenUseCase : PostFCMTokenUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        initFCM()
        setContent {
            PlanDingTheme {
                MainNavHost()
            }
        }
    }

    private fun initFCM() = CoroutineScope(Dispatchers.IO).launch {
        postFCMTokenUseCase(PDFirebaseMessagingService().getFirebaseToken()).onSuccess{

        }.onFailure {

        }
    }

    companion object {
        fun mainIntent(context: Context) = Intent(
            context,
            MainActivity::class.java
        )
    }
}

