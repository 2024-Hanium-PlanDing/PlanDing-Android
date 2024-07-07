package com.comst.presentation.main.group.detail

import androidx.compose.runtime.Immutable
import com.comst.presentation.common.base.UIEvent
import com.comst.presentation.common.base.UISideEffect
import com.comst.presentation.common.base.UIState

class GroupDetailContract {

    @Immutable
    data class GroupDetailUIState(
        val id: Long = 0
    ): UIState

    sealed class GroupDetailUISideEffect: UISideEffect{

    }

    sealed class GroupDetailUIEvent: UIEvent {

    }
}