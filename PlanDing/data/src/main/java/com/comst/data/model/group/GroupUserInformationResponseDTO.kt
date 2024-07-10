package com.comst.data.model.group

import com.comst.domain.model.group.GroupUserInformationModel

data class GroupUserInformationResponseDTO (
    val id: Long,
    val userName: String,
    val email: String,
    val profileImageUrl: String,
)

fun GroupUserInformationResponseDTO.toDomainModel(): GroupUserInformationModel {
    return GroupUserInformationModel(
        id = 0,
        userName = "",
        email = "",
        profileImageUrl = ""
    )
}