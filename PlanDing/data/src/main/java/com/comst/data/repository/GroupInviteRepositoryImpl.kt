package com.comst.data.repository

import com.comst.data.model.groupInvite.GroupInviteParam
import com.comst.data.model.groupInvite.toDomainModel
import com.comst.data.retrofit.ApiHandler
import com.comst.data.retrofit.GroupInviteService
import com.comst.domain.model.groupInvite.GroupInviteModel
import com.comst.domain.model.groupInvite.GroupInviteResponseModel
import com.comst.domain.model.groupInvite.GroupRequestReceivedResponseModel
import com.comst.domain.repository.GroupInviteRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GroupInviteRepositoryImpl @Inject constructor(
    private val groupInviteService: GroupInviteService
): GroupInviteRepository{
    override suspend fun getGroupRequestsReceived(): ApiResult<List<GroupRequestReceivedResponseModel>> {
        return ApiHandler.handle(
            execute = { groupInviteService.getGroupRequestsReceivedApi() },
            mapper = { response -> response.map { it.toDomainModel() }}
        )
    }

    override suspend fun postInviteGroupMember(groupInviteModel: GroupInviteModel): ApiResult<GroupInviteResponseModel> {
        val requestBody = GroupInviteParam(
            groupCode = groupInviteModel.groupCode,
            userCode = groupInviteModel.userCode
        )
        return ApiHandler.handle(
            execute = { groupInviteService.postGroupInviteApi(requestBody) },
            mapper = { response -> response.toDomainModel() }
        )
    }

}