package com.comst.data.usecase.groupRoom

import com.comst.data.converter.MediaImageMultipartConverter
import com.comst.data.model.groupRoom.GroupCreateParam
import com.comst.data.model.groupRoom.toDomainModel
import com.comst.data.retrofit.GroupRoomService
import com.comst.domain.model.file.MediaImage
import com.comst.domain.model.groupRoom.GroupRoomCreate
import com.comst.domain.model.groupRoom.GroupRoomCreateResponseModel
import com.comst.domain.usecase.groupRoom.PostGroupRoomUseCase
import javax.inject.Inject

class PostGroupRoomUseCaseImpl @Inject constructor(
    private val groupRoomService: GroupRoomService,
    private val mediaImageMultipartConverter: MediaImageMultipartConverter
) : PostGroupRoomUseCase {

    override suspend fun invoke(groupRoomCreate: GroupRoomCreate, thumbnail: MediaImage): Result<GroupRoomCreateResponseModel> = kotlin.runCatching {

        val request = GroupCreateParam(
            name = groupRoomCreate.name,
            description = groupRoomCreate.description
        )
        val multipartBodyPart = mediaImageMultipartConverter.toMultipartBodyPart(thumbnail, "thumbnail")
            ?: throw IllegalArgumentException("Invalid file URI")


        groupRoomService.postGroupRoom(requestBody = request, thumbnail = multipartBodyPart).data.toDomainModel()
    }
}