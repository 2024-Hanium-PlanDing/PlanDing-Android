package com.comst.presentation.main.group.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.comst.presentation.ui.theme.PlanDingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupDetailActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val groupCode = intent.getStringExtra(GROUP_CODE) ?: ""
        setContent {
            PlanDingTheme {
                GroupDetailScreen(groupCode = groupCode)
            }
        }
    }

    companion object {

        const val GROUP_CODE = "groupCode"
        fun groupDetailIntent(context: Context,  groupCode: String) = Intent(
            context,
            GroupDetailActivity::class.java
        ).apply { putExtra(GROUP_CODE, groupCode) }
    }
}