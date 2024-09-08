package com.comst.presentation.main.group.create

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.comst.presentation.model.group.GroupCardUIModel
import com.comst.presentation.ui.theme.PlanDingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanDingTheme {
                CreateGroupNavHost(
                    onGroupCreated = { groupCardModel ->
                        val resultIntent = Intent().apply {
                            putExtra(EXTRA_GROUP, groupCardModel)
                        }
                        setResult(CREATE_GROUP, resultIntent)
                        finish()
                    },
                    onCancel = {
                        finish()
                    }
                )
            }
        }
    }

    companion object {
        const val CREATE_GROUP = 200
        const val EXTRA_GROUP = "extra_group"
        fun createGroupIntent(context: Context) = Intent(
            context,
            CreateGroupActivity::class.java
        )

        @Suppress("DEPRECATION")
        fun getGroupResponseFromIntent(intent: Intent): GroupCardUIModel? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(EXTRA_GROUP, GroupCardUIModel::class.java)
            } else {
                intent.getParcelableExtra(EXTRA_GROUP) as? GroupCardUIModel
            }
        }
    }
}