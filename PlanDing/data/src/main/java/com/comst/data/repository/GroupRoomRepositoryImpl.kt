package com.comst.data.repository

import android.content.Context
import com.comst.data.converter.MediaImageMultipartConverter
import com.comst.data.model.groupRoom.GroupCreateParam
import com.comst.data.model.groupRoom.toDomainModel
import com.comst.data.retrofit.GroupRoomService
import com.comst.data.retrofit.apiHandler
import com.comst.domain.model.file.MediaImage
import com.comst.domain.model.groupRoom.GroupRoomCardModel
import com.comst.domain.model.groupRoom.GroupRoomCreate
import com.comst.domain.model.groupRoom.GroupRoomCreateResponseModel
import com.comst.domain.repository.GroupRoomRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GroupRoomRepositoryImpl @Inject constructor(
    private val groupRoomService: GroupRoomService,
    private val mediaImageMultipartConverter: MediaImageMultipartConverter,
    private val context: Context
): GroupRoomRepository {

    override suspend fun getMyGroupRoom(): ApiResult<List<GroupRoomCardModel>> {
        return apiHandler(
            context = context,
            execute = { groupRoomService.getMyGroupRoom() },
            mapper = { response -> response.map { it.toDomainModel() } }
        )
    }

    override suspend fun postGroupRoom(groupRoomCreate: GroupRoomCreate, thumbnail: MediaImage): ApiResult<GroupRoomCreateResponseModel> {
        return apiHandler(
            context = context,
            execute = {
                val request = GroupCreateParam(
                    name = groupRoomCreate.name,
                    description = groupRoomCreate.description
                )
                val multipartBodyPart = mediaImageMultipartConverter.toMultipartBodyPart(thumbnail, "thumbnail")
                    ?: throw IllegalArgumentException("Invalid file URI")
                groupRoomService.postGroupRoom(requestBody = request, thumbnail = multipartBodyPart)
            },
            mapper = { response -> response.toDomainModel() }
        )
    }
}