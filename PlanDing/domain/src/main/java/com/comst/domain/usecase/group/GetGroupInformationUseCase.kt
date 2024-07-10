package com.comst.domain.usecase.group

import com.comst.domain.model.group.GroupInformationModel
import com.comst.domain.repository.GroupRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

data class GetGroupInformationUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupCode: String): ApiResult<GroupInformationModel> {
        return groupRepository.getGroupInfo(groupCode)
    }
}