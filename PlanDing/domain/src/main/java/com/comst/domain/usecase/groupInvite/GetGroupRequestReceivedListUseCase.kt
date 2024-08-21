package com.comst.domain.usecase.groupInvite

import com.comst.domain.model.groupInvite.GroupRequestReceivedResponseModel
import com.comst.domain.repository.GroupInviteRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GetGroupRequestReceivedListUseCase @Inject constructor(
    private val groupInviteRepository: GroupInviteRepository
){
    suspend operator fun invoke(): ApiResult<List<GroupRequestReceivedResponseModel>> {
        return groupInviteRepository.getGroupRequestsReceived()
    }
}