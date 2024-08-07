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
) : BaseViewModel<MyPageUIState, MyPageSideEffect, MyPageIntent, MyPageEvent>(MyPageUIState()) {

    init {
        load()
    }

    override fun handleIntent(intent: MyPageIntent) {

    }

    override fun handleEvent(event: MyPageEvent) {
        when (event) {
            is MyPageEvent.LoadFailure -> onLoadFailure(event.message)
        }
    }

    private fun load() = viewModelScope.launch {
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
                        isLoading = false
                    )
                }
            }
            .onFailure {
            }
        setState { copy(isLoading = false) }
    }

    private fun onLoadFailure(message: String) {
        setToastEffect(message)
    }

    override fun handleError(exception: Exception) {
        super.handleError(exception)
        setToastEffect(exception.message.orEmpty())
    }
}