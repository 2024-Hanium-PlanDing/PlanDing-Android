package com.comst.domain.usecase.groupRoom

import com.comst.domain.model.file.MediaImage
import com.comst.domain.model.groupRoom.GroupRoomCreate
import com.comst.domain.model.groupRoom.GroupRoomCreateResponseModel
import com.comst.domain.repository.GroupRoomRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class PostGroupRoomUseCase @Inject constructor(
    private val groupRoomRepository: GroupRoomRepository
) {
    suspend operator fun invoke(groupRoomCreate: GroupRoomCreate, thumbnail: MediaImage): ApiResult<GroupRoomCreateResponseModel> {
        return groupRoomRepository.postGroupRoom(groupRoomCreate, thumbnail)
    }
}