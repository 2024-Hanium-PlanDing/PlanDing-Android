package com.comst.domain.repository

import com.comst.domain.model.group.GroupCardModel
import com.comst.domain.model.groupFavorite.GroupFavoriteAddResponseModel
import com.comst.domain.util.ApiResult

interface GroupFavoriteRepository {
    suspend fun postFavoriteGroup(
        groupCode: String
    ): ApiResult<GroupFavoriteAddResponseModel>

    suspend fun deleteFavoriteGroup(
        groupCode: String
    ): ApiResult<String>

    suspend fun getFavoriteGroupList():ApiResult<List<GroupCardModel>>
}