package com.comst.domain.repository

import com.comst.domain.model.file.MediaImage
import com.comst.domain.model.group.GroupCardModel
import com.comst.domain.model.group.GroupCreate
import com.comst.domain.model.group.GroupCreateResponseModel
import com.comst.domain.util.ApiResult

interface GroupRepository {
    suspend fun getMyGroup(): ApiResult<List<GroupCardModel>>
    suspend fun postGroup(groupCreate: GroupCreate, thumbnail: MediaImage): ApiResult<GroupCreateResponseModel>
}