package com.comst.domain.usecase.groupRoom

import com.comst.domain.model.groupRoom.GroupRoomCardModel
import com.comst.domain.repository.GroupRoomRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GetMyGroupRoomsUseCase @Inject constructor(
    private val groupRoomRepository: GroupRoomRepository
) {
    suspend operator fun invoke(): ApiResult<List<GroupRoomCardModel>> {
        return groupRoomRepository.getMyGroupRoom()
    }
}