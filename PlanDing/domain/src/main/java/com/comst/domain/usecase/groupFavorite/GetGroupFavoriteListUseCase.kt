package com.comst.domain.usecase.groupFavorite

import com.comst.domain.model.group.GroupCardModel
import com.comst.domain.repository.GroupFavoriteRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GetGroupFavoriteListUseCase @Inject constructor(
    private val groupFavoriteRepository: GroupFavoriteRepository
){
    suspend operator fun invoke(): ApiResult<List<GroupCardModel>> {
        return groupFavoriteRepository.getFavoriteGroupList()
    }
}