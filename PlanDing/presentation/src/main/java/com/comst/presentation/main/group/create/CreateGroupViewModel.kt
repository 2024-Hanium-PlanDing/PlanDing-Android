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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val getImageListUseCase: GetImageListUseCase,
    private val postGroupUseCase: PostGroupUseCase
) : BaseViewModel<CreateGroupUIState, CreateGroupUISideEffect, CreateGroupUIEvent>(CreateGroupUIState()){
    init {
        load()
    }

    override suspend fun handleEvent(event: CreateGroupUIEvent) {
        when(event){
            is CreateGroupUIEvent.SelectGroupImage -> onImageClick(event.image)

            is CreateGroupUIEvent.CreateGroup -> onCreateGroupClick()

            is CreateGroupUIEvent.GroupNameChange -> onGroupNameChange(event.groupName)

            is CreateGroupUIEvent.GroupDescriptionChange -> onGroupDescriptionChange(event.groupDescription)
        }
    }

    private fun load() = viewModelScope.launch {
        setState { copy(isLoading = true) }
        getImageListUseCase().onSuccess {
            setState {
                copy(
                    selectedImage = it.firstOrNull(),
                    images = it
                )
            }
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

        if (currentState.selectedImage == null){
            setEffect(CreateGroupUISideEffect.ShowToast("그룹 이미지는 필수입니다."))
        }else{
            postGroupUseCase(
                GroupCreate(
                    currentState.groupName,
                    currentState.groupDescription
                ),
                currentState.selectedImage!!
            ).onSuccess {
                setEffect(CreateGroupUISideEffect.ShowToast("그룹 생성을 성공했습니다."))
                    setEffect(CreateGroupUISideEffect.SuccessGroupCreation)
            }.onFailure { statusCode, message ->

            }
        }
        setState { copy(isLoading = true) }

    }


}