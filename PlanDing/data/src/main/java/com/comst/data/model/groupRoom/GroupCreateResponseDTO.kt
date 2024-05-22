package com.comst.data.model.groupRoom

import com.comst.domain.model.groupRoom.GroupRoomCreateResponseModel

data class GroupCreateResponseDTO(
    val id: Long,
    val name: String,
    val description: String,
    val code: String,
    val ownerCode: String
)

fun GroupCreateResponseDTO.toDomainModel(): GroupRoomCreateResponseModel {
    return GroupRoomCreateResponseModel(
        id =  id,
        name = name,
        description = description,
        code = code,
        ownerCode = ownerCode
    )
}