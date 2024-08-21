package com.comst.data.model.groupInvite

import com.comst.domain.model.groupInvite.GroupRequestReceivedResponseModel

data class GroupRequestReceivedResponseDTO(
    val inviteCode: String,
    val groupCode: String,
    val groupName: String,
    val invitedUserCode: String,
    val createdAt: String,
)

fun GroupRequestReceivedResponseDTO.toDomainModel(): GroupRequestReceivedResponseModel {
    return GroupRequestReceivedResponseModel(
        inviteCode = inviteCode,
        groupCode = groupCode,
        groupName = groupName,
        invitedUserCode = invitedUserCode,
        createdAt = createdAt
    )
}