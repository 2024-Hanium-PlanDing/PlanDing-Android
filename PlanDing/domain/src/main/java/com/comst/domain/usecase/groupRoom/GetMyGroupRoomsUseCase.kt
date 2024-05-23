package com.comst.domain.usecase.groupRoom

import com.comst.domain.model.groupRoom.GroupRoomCardModel

interface GetMyGroupRoomsUseCase {
    suspend operator fun invoke():Result<List<GroupRoomCardModel>>
}