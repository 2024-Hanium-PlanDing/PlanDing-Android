package com.comst.domain.usecase.groupInvite

import com.comst.domain.repository.GroupInviteRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class DeleteDenyGroupInviteUseCase @Inject constructor(
    private val groupInviteRepository: GroupInviteRepository
){
    suspend operator fun invoke(inviteCode: String): ApiResult<String> {
        return groupInviteRepository.deleteDenyGroupInvite(inviteCode = inviteCode)
    }
}