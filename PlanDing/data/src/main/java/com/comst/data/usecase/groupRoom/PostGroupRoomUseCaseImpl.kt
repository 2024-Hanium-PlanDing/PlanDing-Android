package com.comst.data.usecase.groupRoom

import com.comst.data.model.groupRoom.GroupCreateParam
import com.comst.data.model.groupRoom.toDomainModel
import com.comst.data.retrofit.GroupRoomService
import com.comst.domain.model.groupRoom.GroupRoomCreate
import com.comst.domain.model.groupRoom.GroupRoomCreateResponseModel
import com.comst.domain.usecase.groupRoom.PostGroupRoomUseCase
import javax.inject.Inject

class PostGroupRoomUseCaseImpl @Inject constructor(
    private val groupRoomService: GroupRoomService
) : PostGroupRoomUseCase{

    override suspend fun invoke(groupRoomCreate: GroupRoomCreate): Result<GroupRoomCreateResponseModel> = kotlin.runCatching {

        val requestBody = GroupCreateParam(
            name = groupRoomCreate.name,
            description = groupRoomCreate.description
        )
        groupRoomService.postGroupRoom(requestBody).data.toDomainModel()
    }
}