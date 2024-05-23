package com.comst.data.model.groupRoom

import com.comst.domain.model.groupRoom.GroupRoomCardModel

data class MyGroupRoomDTO(
    val id : Long,
    val name : String,
    val description : String,
    val code : String,
    val ownerCode : String,
    val thumbnailPath : String
)

fun MyGroupRoomDTO.toDomainModel(): GroupRoomCardModel {
    return GroupRoomCardModel(
        groupId = id,
        groupCode = code,
        groupOwnerCode = ownerCode,
        groupName = name,
        groupImageUrl = thumbnailPath,
        groupDescription = description
    )
}
