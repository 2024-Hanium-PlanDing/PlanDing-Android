package com.comst.presentation.main.group

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.comst.domain.model.groupRoom.GroupRoomCardModel
import com.comst.domain.usecase.groupRoom.GetMyGroupRoomsUseCase
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class GroupViewModel @Inject constructor(
    private val getMyGroupRoomsUseCase: GetMyGroupRoomsUseCase
) : ViewModel(), ContainerHost<GroupState, GroupSideEffect> {
    override val container: Container<GroupState, GroupSideEffect> = container(
        initialState = GroupState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(GroupSideEffect.Toast(throwable.message.orEmpty()))
                    Log.d("엥",throwable.message.orEmpty())
                }
            }
        }

    )

    init {
        load()
    }

    fun load() = intent {
        val myGroupRooms = getMyGroupRoomsUseCase().onSuccess {
            reduce {
                state.copy(
                    groupCardModels = it
                )
            }
        }.onFailure { statusCode, message ->

        }


    }

    fun onUIAction(action: GroupUIAction) {
        when (action) {
            is GroupUIAction.GroupCreate -> onCreateGroupClick()
            is GroupUIAction.GroupClick -> onGroupClick(action.id)
        }
    }


    private fun onCreateGroupClick() = intent {
        postSideEffect(GroupSideEffect.NavigateToCreateGroupActivity)
    }

    private fun onGroupClick(id: Long) = intent {
        postSideEffect(GroupSideEffect.NavigateToGroupDetailActivity(id))
    }
}

sealed class GroupUIAction {
    data class GroupClick(val id: Long) : GroupUIAction()
    object GroupCreate : GroupUIAction()
}

@Immutable
data class GroupState(
    val groupCardModels: List<GroupRoomCardModel> = emptyList()
)

sealed interface GroupSideEffect {
    data class Toast(val message: String) : GroupSideEffect
    object NavigateToCreateGroupActivity : GroupSideEffect
    class NavigateToGroupDetailActivity(val id : Long) : GroupSideEffect
}