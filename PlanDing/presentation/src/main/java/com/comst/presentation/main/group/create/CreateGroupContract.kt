package com.comst.presentation.main.group.create

import androidx.compose.runtime.Immutable
import com.comst.domain.model.file.MediaImage
import com.comst.presentation.common.base.UIEvent
import com.comst.presentation.common.base.UISideEffect
import com.comst.presentation.common.base.UIState

class CreateGroupContract {

    @Immutable
    data class CreateGroupUIState(
        val selectedImage : MediaImage? = null,
        val images: List<MediaImage> = emptyList(),
        val groupName: String = "",
        val groupDescription: String = "",
        val isLoading: Boolean = false
    ): UIState

    sealed class CreateGroupUISideEffect: UISideEffect {
        data class ShowToast(val message:String): CreateGroupUISideEffect()
        object SuccessGroupCreation: CreateGroupUISideEffect()
    }

    sealed class CreateGroupUIEvent: UIEvent {
        data class GroupNameChange(val groupName:String) : CreateGroupUIEvent()
        data class GroupDescriptionChange(val groupDescription:String) : CreateGroupUIEvent()
        data class SelectGroupImage(val image:MediaImage) : CreateGroupUIEvent()
        object CreateGroup : CreateGroupUIEvent()
    }
}