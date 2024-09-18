package com.comst.presentation.main.mypage.favoriteGroup

import androidx.compose.runtime.Immutable
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState
import com.comst.presentation.model.group.GroupCardUIModel

class FavoriteGroupContract {

    @Immutable
    data class FavoriteGroupUIState(
        val groupCardModels: List<GroupCardUIModel> = emptyList(),
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false
    ) : UIState

    sealed class FavoriteGroupSideEffect: BaseSideEffect{
        data class NavigateToGroupDetailActivity(val groupCode: String): FavoriteGroupSideEffect()
    }

    sealed class FavoriteGroupIntent: BaseIntent{
        data class GroupCardClick(val groupCode: String) : FavoriteGroupIntent()
        object Refresh : FavoriteGroupIntent()
    }

    sealed class FavoriteGroupEvent: BaseEvent{

    }
}