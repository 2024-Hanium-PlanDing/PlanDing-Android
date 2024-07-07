package com.comst.domain.usecase.group

import com.comst.domain.model.group.GroupCardModel
import com.comst.domain.repository.GroupRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GetMyGroupsUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(): ApiResult<List<GroupCardModel>> {
        return groupRepository.getMyGroup()
    }
}