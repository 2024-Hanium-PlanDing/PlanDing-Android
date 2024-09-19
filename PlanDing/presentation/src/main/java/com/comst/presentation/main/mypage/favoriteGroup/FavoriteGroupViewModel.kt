package com.comst.presentation.main.mypage.favoriteGroup

import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.groupFavorite.GetGroupFavoriteListUseCase
import com.comst.domain.util.onException
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.mypage.favoriteGroup.FavoriteGroupContract.*
import com.comst.presentation.model.group.toGroupCardUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteGroupViewModel @Inject constructor(
    private val groupFavoriteListUseCase: GetGroupFavoriteListUseCase
): BaseViewModel<FavoriteGroupUIState, FavoriteGroupSideEffect, FavoriteGroupIntent, FavoriteGroupEvent>(FavoriteGroupUIState()) {

    init {
        load()
    }

    private fun load() = viewModelScope.launch(coroutineExceptionHandler) {
        setState { copy(isLoading = true) }

        groupFavoriteListUseCase().onSuccess {groupCardModels ->
            setState {
                copy(
                    groupCardModels = groupCardModels.map { it.toGroupCardUIModel() },
                    isRefreshing = false
                )
            }
        }.onFailure {

        }.onException { exception ->
            throw exception
        }

        setState {
            copy(
                isLoading = false,
                isRefreshing = false
            )
        }
    }


    override fun handleIntent(intent: FavoriteGroupIntent) {
        when(intent){
            is FavoriteGroupIntent.GroupCardClick -> setEffect(FavoriteGroupSideEffect.NavigateToGroupDetailActivity(intent.groupCode))
            is FavoriteGroupIntent.Refresh -> onRefresh()
        }
    }

    override fun handleEvent(event: FavoriteGroupEvent) {

    }

    private fun onRefresh() {
        setState { copy(isRefreshing = true) }
        load()
    }


}