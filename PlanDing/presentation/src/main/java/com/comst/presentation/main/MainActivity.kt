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
import com.comst.presentation.ui.theme.PlanDingTheme
import com.comst.presentation.util.PDFirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFCM()
        setContent {
            PlanDingTheme {
                MainNavHost()
            }
        }
    }

    private fun initFCM() = CoroutineScope(Dispatchers.IO).launch {
        val s = PDFirebaseMessagingService().getFirebaseToken()
    }

    companion object {
        fun mainIntent(context: Context) = Intent(
            context,
            MainActivity::class.java
        )
    }
}

