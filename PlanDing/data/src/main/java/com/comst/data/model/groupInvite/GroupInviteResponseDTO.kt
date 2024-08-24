package com.comst.data.model.groupInvite

import com.comst.domain.model.groupInvite.GroupInviteResponseModel
data class GroupInviteResponseDTO(
    val inviteCode : String,
    val groupCode : String,
    val groupName : String,
    val invitedUserCode : String,
    val createdAt : String,
)

fun GroupInviteResponseDTO.toDomainModel(): GroupInviteResponseModel {
    return GroupInviteResponseModel(
        inviteCode = inviteCode,
        groupCode = groupCode,
        groupName = groupName,
        invitedUserCode = invitedUserCode,
        createdAt = createdAt
    )
}