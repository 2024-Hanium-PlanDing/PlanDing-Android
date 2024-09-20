package com.comst.presentation.common.base

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.comst.presentation.auth.AuthActivity
import com.comst.presentation.common.flow.collectAsStateWithLifecycle
import com.comst.presentation.common.flow.collectWithLifecycle

@Composable
fun <S : UIState, A : BaseSideEffect, I : BaseIntent, E : BaseEvent> BaseScreen(
    viewModel: BaseViewModel<S, A, I, E>,
    handleEffect: (A) -> Unit = {},
    content: @Composable (S) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    viewModel.effect.collectWithLifecycle { effect ->
        when (effect) {
            is BaseSideEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
            is BaseSideEffect.NavigateToLogin -> {
                context.startActivity(
                    AuthActivity.authIntent(context)
                )
            }
            else -> handleEffect(effect)
        }
    }

    content(uiState)
}