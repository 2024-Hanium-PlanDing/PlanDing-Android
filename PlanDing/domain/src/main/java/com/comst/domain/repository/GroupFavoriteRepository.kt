package com.comst.domain.repository

import com.comst.domain.model.groupFavorite.GroupFavoriteAddResponseModel
import com.comst.domain.util.ApiResult

interface GroupFavoriteRepository {
    suspend fun postFavoriteGroup(
        groupCode: String
    ): ApiResult<GroupFavoriteAddResponseModel>
}