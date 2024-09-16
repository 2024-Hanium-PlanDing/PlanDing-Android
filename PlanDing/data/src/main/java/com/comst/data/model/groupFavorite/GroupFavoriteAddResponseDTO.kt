package com.comst.data.model.groupFavorite

import com.comst.domain.model.groupFavorite.GroupFavoriteAddResponseModel

data class GroupFavoriteAddResponseDTO(
    val groupName: String,
    val groupCode: String
)

fun GroupFavoriteAddResponseDTO.toDomainModel(): GroupFavoriteAddResponseModel{
    return GroupFavoriteAddResponseModel(
        groupName = groupName,
        groupCode = groupCode
    )
}