package com.comst.data.repository

import com.comst.data.model.group.toDomainModel
import com.comst.data.model.groupFavorite.toDomainModel
import com.comst.data.retrofit.ApiHandler
import com.comst.data.retrofit.GroupFavoriteService
import com.comst.domain.model.group.GroupCardModel
import com.comst.domain.model.groupFavorite.GroupFavoriteAddResponseModel
import com.comst.domain.repository.GroupFavoriteRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GroupFavoriteRepositoryImpl @Inject constructor(
    private val groupFavoriteService: GroupFavoriteService
): GroupFavoriteRepository{

    override suspend fun postFavoriteGroup(groupCode: String): ApiResult<GroupFavoriteAddResponseModel> {
        return ApiHandler.handle(
            execute = { groupFavoriteService.postFavoriteGroupApi(
                groupCode = groupCode
            ) },
            mapper = { response -> response.toDomainModel() }
        )
    }

    override suspend fun deleteFavoriteGroup(groupCode: String): ApiResult<String> {
        return ApiHandler.handle(
            execute = { groupFavoriteService.deleteFavoriteGroupApi(
                groupCode = groupCode
            ) },
            mapper = { response -> response }
        )
    }

    override suspend fun getFavoriteGroupList(): ApiResult<List<GroupCardModel>> {
        return ApiHandler.handle(
            execute = { groupFavoriteService.getFavoriteGroupListApi() },
            mapper = { response -> response.map { it.toDomainModel() } }
        )
    }

}