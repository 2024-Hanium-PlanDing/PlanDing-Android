package com.comst.data.model.group

import com.comst.domain.model.group.GroupUserInformationModel

data class GroupUserInformationResponseDTO (
    val id: Long,
    val userCode: String,
    val userName: String,
    val email: String,
    val profileImageUrl: String,
)

fun GroupUserInformationResponseDTO.toDomainModel(): GroupUserInformationModel {
    return GroupUserInformationModel(
        id = id,
        userCode = userCode,
        userName = userName,
        email = email,
        profileImageUrl = profileImageUrl
    )
}