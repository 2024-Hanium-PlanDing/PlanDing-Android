package com.comst.presentation.main.group.detail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.comst.presentation.ui.theme.PlanDingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupDetailActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val groupCode = intent.getStringExtra("groupCode") ?: ""
        setContent {
            PlanDingTheme {
                GroupDetailScreen(groupCode = groupCode)
            }
        }
    }
}