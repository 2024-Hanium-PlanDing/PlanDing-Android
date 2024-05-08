package com.comst.presentation.main.group.create

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(

) : ViewModel(), ContainerHost<CreateGroupState, CreateGroupSideEffect>{
    override val container: Container<CreateGroupState, CreateGroupSideEffect> = container(
        initialState = CreateGroupState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler{ _, throwable ->
                intent {
                    postSideEffect(CreateGroupSideEffect.Toast(message = throwable.message.orEmpty()))
                }
            }
        }
    )

    fun onUIAction(action: CreateGroupUIAction ){
        when(action){
            is CreateGroupUIAction.GroupNameChange -> onGroupNameChange(action.groupName)
            is CreateGroupUIAction.GroupDescriptionChange -> onGroupDescriptionChange(action.groupDescription)
        }
    }

    private fun onGroupNameChange(groupName: String) = blockingIntent {
        reduce {
            state.copy(groupName = groupName)
        }
    }

    private fun onGroupDescriptionChange(groupDescription: String) = blockingIntent {
        reduce {
            state.copy(groupDescription = groupDescription)
        }
    }
}

sealed class CreateGroupUIAction {
    data class GroupNameChange(val groupName:String) : CreateGroupUIAction()
    data class GroupDescriptionChange(val groupDescription:String) : CreateGroupUIAction()
}

@Immutable
data class CreateGroupState(
    val groupName: String = "",
    val groupDescription: String = ""
)

sealed interface CreateGroupSideEffect{
    data class Toast(val message:String):CreateGroupSideEffect
}