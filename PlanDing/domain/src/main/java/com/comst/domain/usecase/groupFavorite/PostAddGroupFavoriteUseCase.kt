package com.comst.domain.usecase.groupFavorite

import com.comst.domain.model.groupFavorite.GroupFavoriteAddResponseModel
import com.comst.domain.repository.GroupFavoriteRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class PostAddGroupFavoriteUseCase @Inject constructor(
    private val groupFavoriteRepository: GroupFavoriteRepository
){
    suspend operator fun invoke(groupCode: String): ApiResult<GroupFavoriteAddResponseModel>{
        return groupFavoriteRepository.postFavoriteGroup(groupCode = groupCode)
    }
}