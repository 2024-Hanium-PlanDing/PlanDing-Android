package com.comst.domain.repository

import com.comst.domain.model.groupInvite.GroupInviteModel
import com.comst.domain.model.groupInvite.GroupInviteResponseModel
import com.comst.domain.model.groupInvite.GroupRequestReceivedResponseModel
import com.comst.domain.util.ApiResult

interface GroupInviteRepository {
    suspend fun getGroupRequestsReceived(): ApiResult<List<GroupRequestReceivedResponseModel>>
    suspend fun postInviteGroupMember(groupInviteModel: GroupInviteModel): ApiResult<GroupInviteResponseModel>
}