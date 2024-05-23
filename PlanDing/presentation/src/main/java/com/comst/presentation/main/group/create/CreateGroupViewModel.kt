package com.comst.presentation.main.group.create

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.comst.domain.model.file.MediaImage
import com.comst.domain.model.groupRoom.GroupRoomCreate
import com.comst.domain.usecase.file.GetImageListUseCase
import com.comst.domain.usecase.groupRoom.PostGroupRoomUseCase
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
    private val getImageListUseCase: GetImageListUseCase,
    private val postGroupRoomUseCase: PostGroupRoomUseCase
) : ViewModel(), ContainerHost<CreateGroupState, CreateGroupSideEffect>{
    override val container: Container<CreateGroupState, CreateGroupSideEffect> = container(
        initialState = CreateGroupState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler{ _, throwable ->
                intent {
                    postSideEffect(CreateGroupSideEffect.Toast(message = throwable.message.orEmpty()))
                    Log.d("우우",throwable.message.orEmpty())
                }
            }
        }
    )

    init {
        load()
    }

    private fun load() = intent {
        val images = getImageListUseCase()
        reduce {
            state.copy(
                selectedImage = images.firstOrNull(),
                images = images
            )
        }
    }

    fun onUIAction(action: CreateGroupUIAction ){
        when(action){
            is CreateGroupUIAction.GroupNameChange -> onGroupNameChange(action.groupName)
            is CreateGroupUIAction.GroupDescriptionChange -> onGroupDescriptionChange(action.groupDescription)
            is CreateGroupUIAction.SelectGroupImage -> onImageClick(action.image)
            is CreateGroupUIAction.CreateGroupRoom -> onCreateGroupRoomClick()
        }
    }

    private fun onImageClick(image: MediaImage) = intent {
        reduce {
            if (state.selectedImage == image) {
                state.copy(
                    selectedImage = null
                )
            }else{
                state.copy(
                    selectedImage = image
                )
            }
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

    private fun onCreateGroupRoomClick() = intent {
        if (state.selectedImage != null){
            val groupRoomCreate = GroupRoomCreate(
                name = state.groupName,
                description = state.groupDescription
            )
            postGroupRoomUseCase(groupRoomCreate, state.selectedImage!!).getOrThrow()
            postSideEffect(CreateGroupSideEffect.Complete)
        }else{
            postSideEffect(CreateGroupSideEffect.Toast("그룹 이미지는 필수입니다."))
        }

    }
}

sealed class CreateGroupUIAction {
    data class GroupNameChange(val groupName:String) : CreateGroupUIAction()
    data class GroupDescriptionChange(val groupDescription:String) : CreateGroupUIAction()
    data class SelectGroupImage(val image:MediaImage) : CreateGroupUIAction()
    object CreateGroupRoom : CreateGroupUIAction()
}

@Immutable
data class CreateGroupState(
    val selectedImage : MediaImage? = null,
    val images: List<MediaImage> = emptyList(),
    val groupName: String = "",
    val groupDescription: String = ""
)

sealed interface CreateGroupSideEffect{
    data class Toast(val message:String):CreateGroupSideEffect
    object Complete : CreateGroupSideEffect
}