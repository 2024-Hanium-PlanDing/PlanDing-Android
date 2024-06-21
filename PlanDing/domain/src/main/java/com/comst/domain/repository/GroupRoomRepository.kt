package com.comst.domain.repository

import com.comst.domain.model.file.MediaImage
import com.comst.domain.model.groupRoom.GroupRoomCardModel
import com.comst.domain.model.groupRoom.GroupRoomCreate
import com.comst.domain.model.groupRoom.GroupRoomCreateResponseModel
import com.comst.domain.util.ApiResult

interface GroupRoomRepository {
    suspend fun getMyGroupRoom(): ApiResult<List<GroupRoomCardModel>>
    suspend fun postGroupRoom(groupRoomCreate: GroupRoomCreate, thumbnail: MediaImage): ApiResult<GroupRoomCreateResponseModel>
}