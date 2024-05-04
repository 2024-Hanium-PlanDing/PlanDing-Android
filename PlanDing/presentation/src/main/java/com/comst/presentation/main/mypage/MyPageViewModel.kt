package com.comst.presentation.main.mypage

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(

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

    fun onUIAction(action: MyPageUIAction){

    }

}

sealed class MyPageUIAction{

}

@Immutable
class MyPageState(
    val username : String = "",
    val profileImageUrl : String? = null ,
    val favoriteGroupsCount : String = "0",
    val receivedGroupRequestsCount : String = "0"
)

sealed interface MyPageSideEffect {
    data class Toast(val message: String) : MyPageSideEffect
}
