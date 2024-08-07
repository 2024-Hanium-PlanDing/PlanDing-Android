package com.comst.presentation.main.group.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.comst.presentation.ui.theme.PlanDingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanDingTheme {
                CreateGroupNavHost { finish() }
            }
        }
    }

    companion object {
        fun createGroupIntent(context: Context) = Intent(
            context,
            CreateGroupActivity::class.java
        )
    }
}