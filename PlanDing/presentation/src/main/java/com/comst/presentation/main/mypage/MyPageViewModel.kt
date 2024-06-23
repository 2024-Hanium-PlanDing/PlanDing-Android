package com.comst.presentation.main.mypage

import androidx.lifecycle.ViewModel
import com.comst.domain.model.user.UserProfile
import com.comst.domain.usecase.user.GetUserProfileUseCase
import com.comst.domain.util.onFail
import com.comst.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel(), ContainerHost<MyPageState, MyPageSideEffect>{
    override val container: Container<MyPageState, MyPageSideEffect> = container(
        initialState = MyPageState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(MyPageSideEffect.Toast(throwable.message.orEmpty()))
                }
            }
        }
    )

    init {
        load()
    }
    fun onUIAction(action: MyPageUIAction){

    }

    private fun load() = intent {
        getUserProfileUseCase().onSuccess {
            reduce {
                state.copy(
                    username = it.username,
                    userCode = it.userCode,
                    profileImageUrl = it.profileImage,
                    favoriteGroupsCount = it.groupFavorite,
                    receivedGroupRequestsCount = it.groupRequest
                )
            }
        }.onFail {

        }

    }

}

sealed class MyPageUIAction{

}

@Immutable
data class MyPageState(
    val username : String = "",
    val userCode : String = "",
    val profileImageUrl : String? = null ,
    val favoriteGroupsCount : String = "-1",
    val receivedGroupRequestsCount : String = "-1"
)

sealed interface MyPageSideEffect {
    data class Toast(val message: String) : MyPageSideEffect
}
