package com.comst.domain.usecase.groupFavorite

import com.comst.domain.repository.GroupFavoriteRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class DeleteGroupFavoriteUseCase @Inject constructor(
    private val groupFavoriteRepository: GroupFavoriteRepository
){
    suspend operator fun invoke(groupCode: String): ApiResult<String> {
        return groupFavoriteRepository.deleteFavoriteGroup(groupCode = groupCode)
    }
}