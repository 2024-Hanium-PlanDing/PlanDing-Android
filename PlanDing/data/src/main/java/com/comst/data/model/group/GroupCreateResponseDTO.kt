package com.comst.data.model.group

import com.comst.domain.model.group.GroupCreateResponseModel

data class GroupCreateResponseDTO(
    val id: Long,
    val name: String,
    val description: String,
    val code: String,
    val ownerCode: String
)

fun GroupCreateResponseDTO.toDomainModel(): GroupCreateResponseModel {
    return GroupCreateResponseModel(
        id =  id,
        name = name,
        description = description,
        code = code,
        ownerCode = ownerCode
    )
}