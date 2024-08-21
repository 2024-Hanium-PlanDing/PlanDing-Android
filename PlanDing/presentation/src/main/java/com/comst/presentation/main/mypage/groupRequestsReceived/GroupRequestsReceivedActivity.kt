package com.comst.presentation.main.mypage.groupRequestsReceived

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.comst.presentation.ui.theme.PlanDingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupRequestsReceivedActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanDingTheme {
                GroupRequestsReceivedScreen()
            }
        }
    }

    companion object {

        fun groupRequestsReceivedIntent(context: Context) = Intent(
            context,
            GroupRequestsReceivedActivity::class.java
        )
    }
}