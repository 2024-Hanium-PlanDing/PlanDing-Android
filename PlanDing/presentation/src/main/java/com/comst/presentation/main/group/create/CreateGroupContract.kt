package com.comst.presentation.main.group.create

import androidx.compose.runtime.Immutable
import com.comst.domain.model.file.MediaImage
import com.comst.domain.model.group.GroupCreateResponseModel
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState
import com.comst.presentation.model.group.GroupCardUIModel

class CreateGroupContract {

    @Immutable
    data class CreateGroupUIState(
        val selectedImage: MediaImage? = null,
        val images: List<MediaImage> = emptyList(),
        val groupName: String = "",
        val groupDescription: String = "",
        val isLoading: Boolean = false
    ) : UIState

    sealed class CreateGroupSideEffect : BaseSideEffect {
        data class SuccessGroupCreation(val groupCardUIModel: GroupCardUIModel) : CreateGroupSideEffect()
    }

    sealed class CreateGroupIntent : BaseIntent {
        data class GroupNameChange(val groupName: String) : CreateGroupIntent()
        data class GroupDescriptionChange(val groupDescription: String) : CreateGroupIntent()
        data class SelectGroupImage(val image: MediaImage) : CreateGroupIntent()
        object CreateGroup : CreateGroupIntent()
    }

    sealed class CreateGroupEvent : BaseEvent {
        data class LoadFailure(val message: String) : CreateGroupEvent()
    }
}