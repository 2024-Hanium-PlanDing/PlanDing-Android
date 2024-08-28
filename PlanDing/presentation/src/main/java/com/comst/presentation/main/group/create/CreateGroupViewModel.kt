package com.comst.presentation.main.group.create

import androidx.lifecycle.viewModelScope
import com.comst.domain.model.file.MediaImage
import com.comst.domain.model.group.GroupCreate
import com.comst.domain.usecase.file.GetImageListUseCase
import com.comst.domain.usecase.group.PostGroupUseCase
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.create.CreateGroupContract.*
import com.comst.presentation.model.group.toGroupCardUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val getImageListUseCase: GetImageListUseCase,
    private val postGroupUseCase: PostGroupUseCase
) : BaseViewModel<CreateGroupUIState, CreateGroupSideEffect, CreateGroupIntent, CreateGroupEvent>(CreateGroupUIState()) {

    init {
        load()
    }

    override fun handleIntent(intent: CreateGroupIntent) {
        when (intent) {
            is CreateGroupIntent.SelectGroupImage -> onImageClick(intent.image)
            is CreateGroupIntent.CreateGroup -> onCreateGroupClick()
            is CreateGroupIntent.GroupNameChange -> onGroupNameChange(intent.groupName)
            is CreateGroupIntent.GroupDescriptionChange -> onGroupDescriptionChange(intent.groupDescription)
        }
    }

    override fun handleEvent(event: CreateGroupEvent) {
        when (event) {
            is CreateGroupEvent.LoadFailure -> onLoadFailure(event.message)
        }
    }

    private fun load() = viewModelScope.launch {
        setState { copy(isLoading = true) }
        getImageListUseCase().onSuccess {
            setState {
                copy(
                    selectedImage = it.firstOrNull(),
                    images = it,
                )
            }
        }.onFailure {
        }
        setState { copy(isLoading = false) }

    }

    private fun onImageClick(image: MediaImage) {
        setState {
            if (selectedImage == image) {
                copy(selectedImage = null)
            } else {
                copy(selectedImage = image)
            }
        }
    }

    private fun onGroupNameChange(groupName: String) {
        setState {
            copy(groupName = groupName)
        }
    }

    private fun onGroupDescriptionChange(groupDescription: String) {
        setState {
            copy(groupDescription = groupDescription)
        }
    }

    private fun onCreateGroupClick() = viewModelScope.launch {
        if (currentState.selectedImage == null) {
            setToastEffect("그룹 이미지는 필수입니다.")
            return@launch
        }

        postGroupUseCase(
            GroupCreate(
                currentState.groupName,
                currentState.groupDescription
            ),
            currentState.selectedImage!!
        ).onSuccess {
            setToastEffect("그룹 생성을 성공했습니다.")
            setEffect(CreateGroupSideEffect.SuccessGroupCreation(it.toGroupCardUIModel()))
        }.onFailure {
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