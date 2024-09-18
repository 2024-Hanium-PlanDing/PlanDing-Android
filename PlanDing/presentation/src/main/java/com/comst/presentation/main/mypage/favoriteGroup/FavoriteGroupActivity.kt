package com.comst.presentation.main.mypage.favoriteGroup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.comst.presentation.ui.theme.PlanDingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteGroupActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanDingTheme {
                FavoriteGroupScreen()
            }
        }
    }

    companion object {
        fun favoriteGroupIntent(context: Context) = Intent(
            context,
            FavoriteGroupActivity::class.java
        )
    }
}