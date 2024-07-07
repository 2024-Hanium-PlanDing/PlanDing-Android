package com.comst.domain.usecase.group

import com.comst.domain.model.file.MediaImage
import com.comst.domain.model.group.GroupCreate
import com.comst.domain.model.group.GroupCreateResponseModel
import com.comst.domain.repository.GroupRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class PostGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupCreate: GroupCreate, thumbnail: MediaImage): ApiResult<GroupCreateResponseModel> {
        return groupRepository.postGroup(groupCreate, thumbnail)
    }
}