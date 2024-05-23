package com.comst.data.usecase.groupRoom

import com.comst.data.model.groupRoom.toDomainModel
import com.comst.data.retrofit.GroupRoomService
import com.comst.domain.model.groupRoom.GroupRoomCardModel
import com.comst.domain.usecase.groupRoom.GetMyGroupRoomsUseCase
import javax.inject.Inject

class GetMyGroupRoomsUseCaseImpl @Inject constructor(
    private val groupRoomService: GroupRoomService
) : GetMyGroupRoomsUseCase {
    override suspend fun invoke(): Result<List<GroupRoomCardModel>> = kotlin.runCatching{
        groupRoomService.getMyGroupRoom().data.map { it.toDomainModel() }
    }
}