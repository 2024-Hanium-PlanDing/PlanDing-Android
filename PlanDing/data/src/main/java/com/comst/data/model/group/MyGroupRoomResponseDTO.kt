package com.comst.data.model.group

import com.comst.domain.model.group.GroupCardModel

data class MyGroupRoomDTO(
    val id : Long,
    val name : String,
    val description : String,
    val code : String,
    val ownerCode : String,
    val thumbnailPath : String
)

fun MyGroupRoomDTO.toDomainModel(): GroupCardModel {
    return GroupCardModel(
        groupId = id,
        groupCode = code,
        groupOwnerCode = ownerCode,
        groupName = name,
        groupImageUrl = thumbnailPath,
        groupDescription = description
    )
}
