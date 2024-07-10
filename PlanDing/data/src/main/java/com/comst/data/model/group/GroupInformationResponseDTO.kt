package com.comst.data.model.group

import com.comst.domain.model.group.GroupInformationModel

data class GroupInformationResponseDTO(
    val id: Long,
    val name: String,
    val groupCode: String,
    val description: String,
    val thumbnailUrl: String,
    val createdBy: String,
    val users: List<GroupUserInformationResponseDTO>,
)

fun GroupInformationResponseDTO.toDomainModel(): GroupInformationModel{
    return GroupInformationModel(
        id = id,
        name = name,
        groupCode = groupCode,
        description = description,
        thumbnailUrl = thumbnailUrl,
        createdBy = createdBy,
        users = users.map { it.toDomainModel() }
    )
}