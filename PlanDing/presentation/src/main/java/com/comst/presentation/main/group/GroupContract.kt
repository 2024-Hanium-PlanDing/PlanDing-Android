package com.comst.presentation.main.group

import androidx.compose.runtime.Immutable
import com.comst.domain.model.group.GroupCardModel
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState

class GroupContract {

    @Immutable
    data class GroupUIState(
        val groupCardModels: List<GroupCardModel> = emptyList(),
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false
    ) : UIState

    sealed class GroupSideEffect : BaseSideEffect {
        object NavigateToCreateGroupActivity : GroupSideEffect()
        data class NavigateToGroupDetailActivity(val groupCode: String) : GroupSideEffect()
    }

    sealed class GroupIntent : BaseIntent {
        data class GroupCardClick(val groupCode: String) : GroupIntent()
        object GroupCreateClick : GroupIntent()
        object Refresh : GroupIntent()
    }

    sealed class GroupEvent : BaseEvent {
        data class LoadFailure(val message: String) : GroupEvent()
    }
}