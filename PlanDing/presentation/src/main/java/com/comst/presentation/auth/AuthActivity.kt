package com.comst.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.comst.presentation.ui.theme.PlanDingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            PlanDingTheme {
                LoginNavHost()
            }
        }
    }

    companion object {
        fun authIntent(context: Context) = Intent(
            context,
            AuthActivity::class.java
        )
    }
}