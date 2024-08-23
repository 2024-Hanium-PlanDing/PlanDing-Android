package com.comst.domain.usecase.groupInvite

import com.comst.domain.repository.GroupInviteRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GetAcceptGroupInviteUseCase @Inject constructor(
    private val groupInviteRepository: GroupInviteRepository
){
    suspend operator fun invoke(
        groupCode: String,
        inviteCode: String
    ): ApiResult<Unit> {
        return groupInviteRepository.getAcceptGroupInvite(
            groupCode = groupCode,
            inviteCode = inviteCode
        )
    }
}