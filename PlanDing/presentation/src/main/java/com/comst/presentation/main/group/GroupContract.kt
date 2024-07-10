package com.comst.presentation.main.group

import androidx.compose.runtime.Immutable
import com.comst.domain.model.group.GroupCardModel
import com.comst.presentation.common.base.UIEvent
import com.comst.presentation.common.base.UISideEffect
import com.comst.presentation.common.base.UIState

class GroupContract {

    @Immutable
    data class GroupUIState(
        val groupCardModels: List<GroupCardModel> = emptyList(),
        val isLoading: Boolean = false
    ): UIState

    sealed class GroupUISideEffect: UISideEffect {
        data class ShowToast(val message: String): GroupUISideEffect()
        object NavigateToCreateGroupActivity : GroupUISideEffect()
        data class NavigateToGroupDetailActivity(val groupCode: String) : GroupUISideEffect()
    }

    sealed class GroupUIEvent: UIEvent{
        data class GroupCardClick(val groupCode: String): GroupUIEvent()
        object GroupCreateClick: GroupUIEvent()
    }
}