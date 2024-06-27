package com.comst.presentation.main.mypage

import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.user.GetUserProfileUseCase
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.mypage.MyPageContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase
) : BaseViewModel<MyPageUIState, MyPageUISideEffect, MyPageUIEvent>(MyPageUIState()){

    init {
        load()
    }

    override suspend fun handleEvent(event: MyPageUIEvent) {
        when(event){
            else -> {}
        }
    }

    private fun load() = viewModelScope.launch {
        setState { copy(isLoading = true) }
        getUserProfileUseCase().onSuccess {
            setState {
                copy(
                    username = it.username,
                    userCode = it.userCode,
                    profileImageUrl = it.profileImage,
                    favoriteGroupsCount = it.groupFavorite,
                    receivedGroupRequestsCount = it.groupRequest
                )
            }

        }.onFailure { statusCode, message ->

        }
        setState { copy(isLoading = false) }
    }



}