package com.comst.data.repository

import com.comst.data.model.group.GroupInviteParam
import com.comst.data.model.group.toDomainModel
import com.comst.data.retrofit.ApiHandler
import com.comst.data.retrofit.GroupInviteService
import com.comst.domain.model.group.GroupInviteModel
import com.comst.domain.model.group.GroupInviteResponseModel
import com.comst.domain.repository.GroupInviteRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GroupInviteRepositoryImpl @Inject constructor(
    private val groupInviteService: GroupInviteService
): GroupInviteRepository{

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