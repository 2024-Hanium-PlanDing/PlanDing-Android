package com.comst.presentation.main.mypage

import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.user.GetUserProfileUseCase
import com.comst.domain.util.onException
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
) : BaseViewModel<MyPageUIState, MyPageSideEffect, MyPageIntent, MyPageEvent>(MyPageUIState()) {

    init {
        load()
    }

    override fun handleIntent(intent: MyPageIntent) {
        when (intent) {
            is MyPageIntent.GroupRequestsReceivedClick -> setEffect(MyPageSideEffect.NavigateToGroupRequestsReceivedActivity)
            is MyPageIntent.Refresh -> onRefresh()
        }
    }

    override fun handleEvent(event: MyPageEvent) {
        when (event) {
            is MyPageEvent.LoadFailure -> onLoadFailure(event.message)
        }
    }

    private fun load() = viewModelScope.launch(apiExceptionHandler) {
        setState { copy(isLoading = true) }
        getUserProfileUseCase()
            .onSuccess {
                setState {
                    copy(
                        username = it.username,
                        userCode = it.userCode,
                        profileImageUrl = it.profileImage,
                        favoriteGroupsCount = it.groupFavorite,
                        receivedGroupRequestsCount = it.groupRequest,
                        isLoading = false,
                        isRefreshing = false
                    )
                }
            }
            .onFailure {
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

    private fun onLoadFailure(message: String) {
        setToastEffect(message)
    }

    private fun onRefresh() {
        setState { copy(isRefreshing = true) }
        load()
    }
}