package com.comst.data.repository

import com.comst.data.converter.MediaImageMultipartConverter
import com.comst.data.model.group.GroupCreateParam
import com.comst.data.model.group.toDomainModel
import com.comst.data.retrofit.ApiHandler
import com.comst.data.retrofit.GroupService
import com.comst.domain.model.file.MediaImage
import com.comst.domain.model.group.GroupCardModel
import com.comst.domain.model.group.GroupCreate
import com.comst.domain.model.group.GroupCreateResponseModel
import com.comst.domain.model.group.GroupInformationModel
import com.comst.domain.repository.GroupRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupService: GroupService,
    private val mediaImageMultipartConverter: MediaImageMultipartConverter,
): GroupRepository {

    override suspend fun getMyGroup(): ApiResult<List<GroupCardModel>> {
        return ApiHandler.handle(
            execute = { groupService.getMyGroup() },
            mapper = { response -> response.map { it.toDomainModel() } }
        )
    }

    override suspend fun postGroup(groupCreate: GroupCreate, thumbnail: MediaImage): ApiResult<GroupCreateResponseModel> {
        return ApiHandler.handle(
            execute = {
                val request = GroupCreateParam(
                    name = groupCreate.name,
                    description = groupCreate.description
                )
                val multipartBodyPart = mediaImageMultipartConverter.toMultipartBodyPart(thumbnail, "thumbnail")
                    ?: throw IllegalArgumentException("Invalid file URI")
                groupService.postGroup(requestBody = request, thumbnail = multipartBodyPart)
            },
            mapper = { response -> response.toDomainModel() }
        )
    }

    override suspend fun getGroupInfo(groupId: Long): ApiResult<GroupInformationModel> {
        return ApiHandler.handle(
            execute = { groupService.getGroupInformation(groupId) },
            mapper = { response ->  response.toDomainModel()}
        )
    }
}